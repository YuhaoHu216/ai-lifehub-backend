package space.huyuhao.voucher.service.impl;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.huyuhao.voucher.mapper.VoucherOrderMapper;
import space.huyuhao.voucher.mq.OrderProducer;
import space.huyuhao.voucher.po.VoucherOrder;
import space.huyuhao.voucher.service.VoucherOrderService;
import space.huyuhao.voucher.utils.OrderMessage;
import space.huyuhao.voucher.utils.RedisIdWorker;
import space.huyuhao.voucher.utils.UserHolder;
import space.huyuhao.voucher.vo.Result;


import java.util.Collections;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author hyh
 * @since 2025-10-11
 */
@Slf4j
@Service
public class VoucherOrderServiceImpl implements VoucherOrderService {

    @Resource
    private VoucherOrderMapper voucherOrderMapper;

    @Resource
    private RedisIdWorker redisIdWorker;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private  RedissonClient redissonClient;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private OrderProducer orderProducer;

    // 加载脚本
    private static final DefaultRedisScript<Long> SECKILL_SCRIPT;
    static {
        SECKILL_SCRIPT = new DefaultRedisScript<>();
        SECKILL_SCRIPT.setLocation(new ClassPathResource("seckill.lua"));
        SECKILL_SCRIPT.setResultType(Long.class);
    }

    @Override
    public Result seckillVoucher(Long voucherId) {
        //获取用户
        Long userId = UserHolder.getUser().getId();
        long orderId = redisIdWorker.nextId("order");
        // 1.执行lua脚本
        Long result = stringRedisTemplate.execute(
                SECKILL_SCRIPT,
                Collections.emptyList(),
                voucherId.toString(), userId.toString(), String.valueOf(orderId)
        );
        int r = result.intValue();
        // 2.判断结果是否为0
        if (r != 0) {
            // 2.1.不为0 ，代表没有购买资格
            return Result.error(r == 1 ? "库存不足" : "不能重复下单");
        }
        // 如果到这里说明扣库存成功，可以异步下单
        OrderMessage orderMessage = new OrderMessage(userId,voucherId,orderId);
        // 用封装的生产者发送消息到 RabbitMQ
        orderProducer.sendOrderMessage(orderMessage);
        return Result.success(orderId);
    }
    // 判断是否购买和创建订单逻辑
    @Transactional
    public Result createVoucherOrder(OrderMessage orderMessage){
            // 因为异步处理的原因所以不用threadlocal,在方法里传递userId
            Long userId = orderMessage.getUserId();
            Long voucherId = orderMessage.getVoucherId();
            int count = voucherOrderMapper.getOrderCountByUserId(userId);
            if(count > 0){
                return Result.error("用户已经购买一次");
            }
            //5，扣减库存
            boolean success = voucherOrderMapper.updateStock(voucherId);
            if (!success) {
                //扣减库存
                return Result.error("库存不足！");
            }
            //6.创建订单
            VoucherOrder voucherOrder = new VoucherOrder();
            // 使用传递的orderId
            long orderId = orderMessage.getOrderId();
            voucherOrder.setId(orderId);
            // 6.2.用户id
            voucherOrder.setUserId(userId);
            // 6.3.代金券id
            voucherOrder.setVoucherId(voucherId);
            voucherOrderMapper.saveOrder(voucherOrder);
            return Result.success(orderId);

    }

}
