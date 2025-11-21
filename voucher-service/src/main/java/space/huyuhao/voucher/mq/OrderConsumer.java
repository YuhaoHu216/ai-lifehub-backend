package space.huyuhao.voucher.mq;



import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import space.huyuhao.voucher.service.VoucherOrderService;
import space.huyuhao.voucher.utils.OrderMessage;


/**
 * @author  hyh
 * @since  2025-11-5
 */
@Slf4j
@Component
public class OrderConsumer {


    private final VoucherOrderService voucherOrderService;

    public OrderConsumer(VoucherOrderService voucherOrderService) {
        this.voucherOrderService = voucherOrderService;
    }

    // 监听 RabbitMQ 队列
    @RabbitListener(queues = "order.create.queue")
    public void onMessage(OrderMessage orderMessage) {
        log.info("接收到订单创建消息,优惠卷号：{}", orderMessage.getVoucherId());
        try {
            // 调用业务逻辑创建订单
            voucherOrderService.createVoucherOrder(orderMessage);
        } catch (Exception e) {
            log.error("订单创建失败：{}", orderMessage.getVoucherId(), e);
            // TODO: 可增加重试、补偿机制
        }
    }
}

