package space.huyuhao.service;


import space.huyuhao.po.VoucherOrder;
import space.huyuhao.vo.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
public interface VoucherOrderService {

    // 购买秒杀优惠卷
    Result seckillVoucher(Long voucherId);

    // 为代理创建的方法
//    void createVoucherOrder(VoucherOrder voucherOrder);
}
