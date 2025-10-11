package space.huyuhao.mapper;


import org.apache.ibatis.annotations.Mapper;
import space.huyuhao.po.SeckillVoucher;
import space.huyuhao.po.Voucher;
import space.huyuhao.po.VoucherOrder;
import space.huyuhao.service.VoucherService;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hyh
 * @since 2025-10-11
 */
@Mapper
public interface VoucherOrderMapper  {

    // 查询订单数量 todo 实现方法
    SeckillVoucher selectById(Long voucherId);
    // 扣减库存
    boolean updateStock(Long voucherId);
    // 保存订单
    void saveOrder(VoucherOrder voucherOrder);
}
