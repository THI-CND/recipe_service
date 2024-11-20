package de.benedikt_schwering.thicnd.adapters.in.rest.dto;

import de.benedikt_schwering.thicnd.domain.model.QuantifiedIngredient;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuantifiedIngredientResponse {
    private String id;
    private String ingredient;
    private double quantity;

    public static QuantifiedIngredientResponse fromQuantifiedIngredient(QuantifiedIngredient quantifiedIngredient) {
        return new QuantifiedIngredientResponse(
                quantifiedIngredient.getId(),
                quantifiedIngredient.getIngredient(),
                quantifiedIngredient.getQuantity()
        );
    }
}
