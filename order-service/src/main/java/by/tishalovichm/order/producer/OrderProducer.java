package by.tishalovichm.order.producer;

import by.tishalovichm.order.model.Order;
import by.tishalovichm.order.model.OrderEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderProducer {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(OrderProducer.class);

    @Value("${spring.rabbitmq.exchange.name}")
    private String exchangeName;

    @Value("${spring.rabbitmq.routing-key.name}")
    private String routingKeyName;

    private final RabbitTemplate rabbitTemplate;

    public void produce(Order order) {
        order.setId(UUID.randomUUID().toString());

        LOGGER.info(String.format("Produced order: %s", order));

        OrderEvent event = new OrderEvent(
                "pending",
                "some message",
                order
        );

        rabbitTemplate.convertAndSend(exchangeName, routingKeyName, event);
    }

}
