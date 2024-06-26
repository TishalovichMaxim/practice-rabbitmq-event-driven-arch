package by.tishalovichm.order.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {

    @Bean
    public Queue stockQueue(
            @Value("${spring.rabbitmq.queue.order-stock.name}") String queueName) {

        return new Queue(queueName);
    }

    @Bean
    public Queue emailQueue(
            @Value("${spring.rabbitmq.queue.order-email.name}") String queueName) {

        return new Queue(queueName);
    }

    @Bean
    public TopicExchange exchange(
            @Value("${spring.rabbitmq.exchange.name}") String exchangeName) {

        return new TopicExchange(exchangeName);
    }

    @Bean
    public Binding stockBinding(
            @Value("${spring.rabbitmq.routing-key.name}") String routingKey,
            @Qualifier("stockQueue") Queue queue, TopicExchange exchange) {

        return BindingBuilder.bind(queue)
                .to(exchange)
                .with(routingKey);
    }

    @Bean
    public Binding emailBinding(
            @Value("${spring.rabbitmq.routing-key.name}") String routingKey,
            @Qualifier("emailQueue") Queue queue, TopicExchange exchange) {

        return BindingBuilder.bind(queue)
                .to(exchange)
                .with(routingKey);
    }

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }

    @Bean
    ApplicationRunner runner(ConnectionFactory cf) {
        return args -> cf.createConnection().close();
    }

}
