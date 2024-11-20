package de.benedikt_schwering.thicnd.adapters.in.rest.dto;

import de.benedikt_schwering.thicnd.domain.model.Recipe;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
@AllArgsConstructor
public class RecipeRequest {
    @NonNull
    private String name;
    @NonNull
    private String description;
    @NonNull
    private List<StepRequest> steps;

    public Recipe toRecipe() {
        return new Recipe(
                getName(),
                getDescription(),
                steps.stream().map(StepRequest::toStep).toList()
        );
    }
}
