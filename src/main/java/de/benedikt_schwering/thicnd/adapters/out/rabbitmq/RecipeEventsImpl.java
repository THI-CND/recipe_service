package de.benedikt_schwering.thicnd.adapters.out.rabbitmq;

import de.benedikt_schwering.thicnd.adapters.out.rabbitmq.dto.RecipeDeletedEvent;
import de.benedikt_schwering.thicnd.adapters.out.rabbitmq.dto.RecipeEvent;
import de.benedikt_schwering.thicnd.domain.model.Recipe;
import de.benedikt_schwering.thicnd.ports.out.RecipeEvents;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RecipeEventsImpl implements RecipeEvents {
    private final RabbitTemplate rabbitTemplate;

    public RecipeEventsImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void recipeCreated(Recipe recipe) {
        rabbitTemplate.convertAndSend(
                "cnd-recipe-management",
                "recipe.created",
                RecipeEvent.fromRecipe(recipe)
        );
    }

    @Override
    public void recipeUpdated(Recipe recipe) {
        rabbitTemplate.convertAndSend(
                "cnd-recipe-management",
                "recipe.updated",
                RecipeEvent.fromRecipe(recipe)
        );
    }

    @Override
    public void recipeDeleted(String id) {
        rabbitTemplate.convertAndSend(
                "cnd-recipe-management",
                "recipe.deleted",
                RecipeDeletedEvent.fromId(id)
        );
    }
}
