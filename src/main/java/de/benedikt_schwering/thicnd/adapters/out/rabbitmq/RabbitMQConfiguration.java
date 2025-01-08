package de.benedikt_schwering.thicnd.adapters.out.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"development", "production"})
public class RabbitMQConfiguration {
    @Value("${service.rabbitmq-exchange}")
    private String rabbitMQExchangeName;

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(rabbitMQExchangeName);
    }

    @Bean
    @Profile("development")
    public Queue queue() {
        return new Queue("recipe.events");
    }

    @Bean
    @Profile("development")
    public Declarable testBinding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("recipe.*");
    }
}
