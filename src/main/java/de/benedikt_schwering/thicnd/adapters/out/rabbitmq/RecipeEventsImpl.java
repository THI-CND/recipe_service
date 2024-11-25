package de.benedikt_schwering.thicnd.adapters.out.rabbitmq;

import de.benedikt_schwering.thicnd.adapters.out.rabbitmq.dto.RecipeDeletedEvent;
import de.benedikt_schwering.thicnd.adapters.out.rabbitmq.dto.RecipeEvent;
import de.benedikt_schwering.thicnd.domain.model.Recipe;
import de.benedikt_schwering.thicnd.ports.out.RecipeEvents;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RecipeEventsImpl implements RecipeEvents {
    private final RabbitTemplate rabbitTemplate;
    private final TopicExchange exchange;

    public RecipeEventsImpl(RabbitTemplate rabbitTemplate, TopicExchange exchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
    }

    @Override
    public void recipeCreated(Recipe recipe) {
        rabbitTemplate.convertAndSend(
                exchange.getName(),
                "recipe.created",
                RecipeEvent.fromRecipe(recipe).toJSON()
        );
    }

    @Override
    public void recipeUpdated(Recipe recipe) {
        rabbitTemplate.convertAndSend(
                exchange.getName(),
                "recipe.updated",
                RecipeEvent.fromRecipe(recipe).toJSON()
        );
    }

    @Override
    public void recipeDeleted(String id) {
        rabbitTemplate.convertAndSend(
                exchange.getName(),
                "recipe.deleted",
                RecipeDeletedEvent.fromId(id).toJSON()
        );
    }
}
