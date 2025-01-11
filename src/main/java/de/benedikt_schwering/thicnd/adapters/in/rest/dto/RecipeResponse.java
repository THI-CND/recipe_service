package de.benedikt_schwering.thicnd.adapters.in.rest.dto;

import de.benedikt_schwering.thicnd.domain.model.Recipe;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RecipeResponse {
    private String id;
    private String name;
    private String author;
    private String description;
    private List<StepResponse> steps;

    public static RecipeResponse fromRecipe(Recipe recipe) {
        return new RecipeResponse(
                recipe.getId(),
                recipe.getName(),
                recipe.getAuthor(),
                recipe.getDescription(),
                recipe.getSteps().stream().map(StepResponse::fromStep).toList()
        );
    }
}
