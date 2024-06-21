package by.tishalovichm.stock.consumer;

import by.tishalovichm.stock.model.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(OrderConsumer.class);

    @RabbitListener(queues = {"${spring.rabbitmq.queue.order-stock.name}"})
    public void listenOrder(OrderEvent orderEvent) {
        LOGGER.info(String.format("Stock: %s", orderEvent.getOrder()));
    }

}
