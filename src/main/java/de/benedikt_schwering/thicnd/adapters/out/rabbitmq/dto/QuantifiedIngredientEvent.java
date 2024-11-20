package de.benedikt_schwering.thicnd.adapters.out.rabbitmq.dto;

import de.benedikt_schwering.thicnd.domain.model.QuantifiedIngredient;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuantifiedIngredientEvent {
    private String id;
    private String ingredient;
    private double quantity;

    public static QuantifiedIngredientEvent fromQuantifiedIngredient(QuantifiedIngredient quantifiedIngredient) {
        return new QuantifiedIngredientEvent(
                quantifiedIngredient.getId(),
                quantifiedIngredient.getIngredient(),
                quantifiedIngredient.getQuantity()
        );
    }
}
