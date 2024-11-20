package de.benedikt_schwering.thicnd.adapters.in.rest.dto;

import de.benedikt_schwering.thicnd.domain.model.Step;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
@AllArgsConstructor
public class StepRequest {
    @NonNull
    private List<QuantifiedIngredientRequest> quantifiedIngredients;
    @NonNull
    private String description;

    public Step toStep() {
        return new Step(
                quantifiedIngredients.stream().map(QuantifiedIngredientRequest::toQuantifiedIngredient).toList(),
                getDescription()
        );
    }
}
