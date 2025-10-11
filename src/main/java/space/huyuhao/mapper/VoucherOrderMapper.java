package space.huyuhao.mapper;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
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

    // 查询订单数量
    @Select("select * from tb_seckill_voucher where voucher_id = #{voucherId}")
    SeckillVoucher selectById(Long voucherId);
    // 扣减库存
//    @Update("update tb_seckill_voucher set stock = stock - 1 where voucher_id = #{voucherId}")
    boolean updateStock(Long voucherId);
    // 保存订单
//    @Insert("insert into tb_voucher_order(id, user_id, voucher_id, pay_type, status, create_time, pay_time, use_time, refund_time, update_time) " +
//            "values " +
//            "(#{id},#{userId},#{voucherId},#{payType},#{status},#{createTime},#{payTime},#{useTime},#{refundTime},#{updateTime})")
    void saveOrder(VoucherOrder voucherOrder);
}
