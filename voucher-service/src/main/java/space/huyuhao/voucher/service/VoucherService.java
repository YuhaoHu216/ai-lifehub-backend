package space.huyuhao.voucher.service;

import org.springframework.stereotype.Service;
import space.huyuhao.voucher.po.Voucher;
import space.huyuhao.voucher.vo.Result;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
@Service
public interface VoucherService {

    // 查询店铺的优惠卷列表
    Result queryVoucherOfShop(Long shopId);

    // 新增秒杀卷
    void addSeckillVoucher(Voucher voucher);

    // 新增普通卷
    void saveNormalVoucher(Voucher voucher);
}
