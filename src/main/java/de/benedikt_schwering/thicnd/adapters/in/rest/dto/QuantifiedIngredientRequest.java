package de.benedikt_schwering.thicnd.adapters.in.rest.dto;

import de.benedikt_schwering.thicnd.domain.model.QuantifiedIngredient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class QuantifiedIngredientRequest {
    @NonNull
    private long ingredient;
    @NonNull
    private double quantity;

    public QuantifiedIngredient toQuantifiedIngredient() {
        return new QuantifiedIngredient(
                getIngredient(),
                getQuantity()
        );
    }
}
