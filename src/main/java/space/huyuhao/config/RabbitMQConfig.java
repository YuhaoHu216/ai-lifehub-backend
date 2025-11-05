package space.huyuhao.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author hyh
 * @since 2025-11-05
 */
@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "order.exchange";
    public static final String QUEUE_NAME = "order.create.queue";
    public static final String ROUTING_KEY = "order.create";

    // 为消息做序列化和反序列化
    @Bean
    public MessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    // 声明交换机
    @Bean
    public Exchange orderExchange() {
        return ExchangeBuilder.topicExchange(EXCHANGE_NAME).durable(true).build();
    }

    // 声明队列
    @Bean
    public Queue orderQueue() {
        return QueueBuilder.durable(QUEUE_NAME).build();
    }

    // 绑定交换机和队列
    @Bean
    public Binding binding() {
        return BindingBuilder
                .bind(orderQueue())
                .to(orderExchange())
                .with(ROUTING_KEY)
                .noargs();
    }
}

