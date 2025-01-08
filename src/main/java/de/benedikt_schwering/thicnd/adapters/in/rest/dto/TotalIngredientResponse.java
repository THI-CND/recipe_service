package de.benedikt_schwering.thicnd.adapters.in.rest.dto;

import de.benedikt_schwering.thicnd.domain.model.QuantifiedIngredient;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TotalIngredientResponse {
    private long ingredient;
    private double quantity;

    public static TotalIngredientResponse fromQuantifiedIngredient(QuantifiedIngredient quantifiedIngredient) {
        return new TotalIngredientResponse(
                quantifiedIngredient.getIngredient(),
                quantifiedIngredient.getQuantity()
        );
    }
}
