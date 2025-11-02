package space.huyuhao.service;


import space.huyuhao.po.VoucherOrder;
import space.huyuhao.vo.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hyh
 * @since 2025-11-01
 */
public interface VoucherOrderService {

    // 购买秒杀优惠卷
    Result seckillVoucher(Long voucherId) throws InterruptedException;

    // 为代理创建的方法
    Result createVoucherOrder(Long voucherId);
}
