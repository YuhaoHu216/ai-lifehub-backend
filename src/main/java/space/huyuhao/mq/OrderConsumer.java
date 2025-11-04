package space.huyuhao.mq;



import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import space.huyuhao.service.VoucherOrderService;

@Slf4j
@Component
public class OrderConsumer {


    private final VoucherOrderService voucherOrderService;

    public OrderConsumer(VoucherOrderService voucherOrderService) {
        this.voucherOrderService = voucherOrderService;
    }

    // 监听 RabbitMQ 队列
    @RabbitListener(queues = "order.create.queue")
    public void onMessage(Long voucherId) {
        log.info("接收到订单创建消息,优惠卷号：{}", voucherId);
        try {
            // 调用业务逻辑创建订单
            voucherOrderService.createVoucherOrder(voucherId);
        } catch (Exception e) {
            log.error("订单创建失败：{}", voucherId, e);
            // TODO: 可增加重试、补偿机制
        }
    }
}

