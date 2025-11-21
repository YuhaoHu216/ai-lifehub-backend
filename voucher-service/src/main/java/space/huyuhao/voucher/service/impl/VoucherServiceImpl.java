package space.huyuhao.voucher.service.impl;


import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.huyuhao.voucher.mapper.VoucherMapper;
import space.huyuhao.voucher.po.SeckillVoucher;
import space.huyuhao.voucher.po.Voucher;
import space.huyuhao.voucher.service.VoucherService;
import space.huyuhao.voucher.vo.Result;

import java.util.List;

import static space.huyuhao.voucher.constants.RedisConstants.SECKILL_STOCK_KEY;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hyh
 * @since 2025-10-11
 */
@Service
public class VoucherServiceImpl implements VoucherService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private VoucherMapper voucherMapper;

    /**
     * 查询店铺优惠卷列表
     * @param shopId
     * @return
     */
    @Override
    public Result queryVoucherOfShop(Long shopId) {
        // 查询优惠券信息
        List<Voucher> vouchers = voucherMapper.queryVoucherOfShop(shopId);
        // 返回结果
        return Result.success(vouchers);
    }

    // 新增秒杀卷
    @Override
    @Transactional
    public void addSeckillVoucher(Voucher voucher) {
        // 保存优惠券
        saveNormalVoucher(voucher);
        // 保存秒杀信息
        SeckillVoucher seckillVoucher = new SeckillVoucher();
        seckillVoucher.setVoucherId(voucher.getId());
        seckillVoucher.setStock(voucher.getStock());
        seckillVoucher.setCreateTime(voucher.getCreateTime());
        seckillVoucher.setUpdateTime(voucher.getUpdateTime());
        seckillVoucher.setBeginTime(voucher.getBeginTime());
        seckillVoucher.setEndTime(voucher.getEndTime());
        voucherMapper.saveSeckillVoucher(seckillVoucher);
        // 保存秒杀库存到Redis
        stringRedisTemplate.opsForValue().set(SECKILL_STOCK_KEY + voucher.getId(), voucher.getStock().toString());
    }

    /**
     * 新增普通卷
     * @param voucher
     */
    public void saveNormalVoucher(Voucher voucher) {
        voucherMapper.saveNormalVoucher(voucher);
    }
}
