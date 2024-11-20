package de.benedikt_schwering.thicnd.adapters.out.rabbitmq.dto;

import de.benedikt_schwering.thicnd.domain.model.Recipe;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RecipeEvent {
    private String id;
    private String name;
    private String description;
    private List<StepEvent> steps;

    public static RecipeEvent fromRecipe(Recipe recipe) {
        return new RecipeEvent(
                recipe.getId(),
                recipe.getName(),
                recipe.getDescription(),
                recipe.getSteps().stream().map(StepEvent::fromStep).toList()
        );
    }
}
