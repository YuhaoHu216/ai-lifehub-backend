package space.huyuhao.controller;



import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import space.huyuhao.service.VoucherOrderService;
import space.huyuhao.vo.Result;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hyh
 * @since 2025-10-11
 */
@RestController
@RequestMapping("/voucher-order")
public class VoucherOrderController {

    @Resource
    private VoucherOrderService voucherOrderService;
    @PostMapping("seckill/{id}")
    public Result seckillVoucher(@PathVariable("id") Long voucherId) {
        return voucherOrderService.seckillVoucher(voucherId);
    }
}
