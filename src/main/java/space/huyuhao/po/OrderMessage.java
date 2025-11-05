package space.huyuhao.po;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author hyh
 * @since 2025-11-05
 */

@Data
@AllArgsConstructor
public class OrderMessage{
    private Long userId;        // 用户id
    private Long voucherId;     // 优惠卷号
    private Long orderId;       // 订单号
}
