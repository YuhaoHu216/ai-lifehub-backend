package space.huyuhao.mapper;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import space.huyuhao.po.SeckillVoucher;
import space.huyuhao.po.Voucher;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hyh
 * @since 2025-10-11
 */
public interface VoucherMapper {

    // 查询店铺优惠卷列表
    @Select("select * from tb_voucher where shop_id = #{shopId}")
    List<Voucher> queryVoucherOfShop(Long shopId);

    // 新增普通卷
    @Insert("insert into tb_voucher(shop_id, title, sub_title, rules, pay_value, actual_value,type,status,create_time,update_time) " +
            "values (#{shopId},#{title},#{subTitle},#{rules},#{payValue}," +
            "#{actualValue},#{type},#{status},#{createTime},#{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void saveNormalVoucher(Voucher voucher);

    // 新增秒杀卷
    @Insert("insert into tb_seckill_voucher(voucher_id, stock, create_time,begin_time, end_time, update_time) " +
            "values" +
            " (#{voucherId},#{stock},#{createTime},#{beginTime},#{endTime},#{updateTime})")
    void saveSeckillVoucher(SeckillVoucher seckillVoucher);
}
