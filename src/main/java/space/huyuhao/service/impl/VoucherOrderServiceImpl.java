package space.huyuhao.service.impl;

import cn.hutool.core.bean.BeanUtil;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.aop.framework.AopContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.huyuhao.mapper.VoucherOrderMapper;
import space.huyuhao.po.SeckillVoucher;
import space.huyuhao.po.VoucherOrder;
import space.huyuhao.service.VoucherOrderService;
import space.huyuhao.service.VoucherService;
import space.huyuhao.utils.RedisIdWorker;
import space.huyuhao.utils.UserHolder;
import space.huyuhao.vo.Result;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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


    @Override
    public Result seckillVoucher(Long voucherId) {
        // 1.查询优惠券
        SeckillVoucher voucher = voucherOrderMapper.selectById(voucherId);
        // 2.判断秒杀是否开始
        if (voucher.getBeginTime().isAfter(LocalDateTime.now())) {
            // 尚未开始
            return Result.error("秒杀尚未开始！");
        }
        // 3.判断秒杀是否已经结束
        if (voucher.getEndTime().isBefore(LocalDateTime.now())) {
            // 尚未开始
            return Result.error("秒杀已经结束！");
        }

        // 4.判断库存是否充足
        if (voucher.getStock() < 1) {
            // 库存不足
            return Result.error("库存不足！");
        }
        Long userId = UserHolder.getUser().getId();
        synchronized (userId.toString().intern()){
            // 获取代理对象
            VoucherOrderService proxy = (VoucherOrderService) AopContext.currentProxy();
            return proxy.createVoucherOrder(voucherId);
        }
    }
    // 判断是否购买和创建订单逻辑
    @Transactional
    public Result createVoucherOrder(Long voucherId){
        // 获取用户id
        Long userId = UserHolder.getUser().getId();

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
            // 6.1.订单id
            long orderId = redisIdWorker.nextId("order");
            voucherOrder.setId(orderId);
            // 6.2.用户id
            userId = UserHolder.getUser().getId();
            voucherOrder.setUserId(userId);
            // 6.3.代金券id
            voucherOrder.setVoucherId(voucherId);
            voucherOrderMapper.saveOrder(voucherOrder);
            return Result.success(orderId);

    }

}
