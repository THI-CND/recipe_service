package de.benedikt_schwering.thicnd.adapters.out.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange("recipe.events");
    }

    @Bean
    public Queue queue() {
        return new Queue("recipe.events");
    }

    @Bean
    public Declarable testBinding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("recipe.*");
    }
}
