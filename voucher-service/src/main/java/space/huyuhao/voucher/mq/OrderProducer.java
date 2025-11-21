package space.huyuhao.voucher.mq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import space.huyuhao.voucher.config.RabbitMQConfig;
import space.huyuhao.voucher.utils.OrderMessage;

/**
 * @author  hyh
 * @since  2025-11-5
 */
@Component
public class OrderProducer {

    private final RabbitTemplate rabbitTemplate;

    public OrderProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendOrderMessage(OrderMessage orderMessage) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                RabbitMQConfig.ROUTING_KEY,
                orderMessage
        );
    }
}

