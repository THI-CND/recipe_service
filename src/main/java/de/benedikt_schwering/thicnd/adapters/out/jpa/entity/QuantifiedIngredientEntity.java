package de.benedikt_schwering.thicnd.adapters.out.jpa.entity;

import de.benedikt_schwering.thicnd.domain.model.QuantifiedIngredient;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuantifiedIngredientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String ingredient;
    private double quantity;

    public static QuantifiedIngredientEntity fromQuantifiedIngredient(QuantifiedIngredient quantifiedIngredient) {
        return new QuantifiedIngredientEntity(
                quantifiedIngredient.getId(),
                quantifiedIngredient.getIngredient(),
                quantifiedIngredient.getQuantity()
        );
    }

    public QuantifiedIngredient toQuantifiedIngredient() {
        return new QuantifiedIngredient(
                getId(),
                getIngredient(),
                getQuantity()
        );
    }
}
