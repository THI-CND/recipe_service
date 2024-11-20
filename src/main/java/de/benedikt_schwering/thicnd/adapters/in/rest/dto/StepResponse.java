package de.benedikt_schwering.thicnd.adapters.in.rest.dto;

import de.benedikt_schwering.thicnd.domain.model.Step;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class StepResponse {
    private String id;
    private List<QuantifiedIngredientResponse> quantifiedIngredients;
    private String description;

    public static StepResponse fromStep(Step step) {
        return new StepResponse(
                step.getId(),
                step.getQuantifiedIngredients().stream().map(QuantifiedIngredientResponse::fromQuantifiedIngredient).toList(),
                step.getDescription()
        );
    }
}
