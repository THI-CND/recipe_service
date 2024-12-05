package de.benedikt_schwering.thicnd.ports.out;

import de.benedikt_schwering.thicnd.domain.model.QuantifiedIngredient;

import java.util.Optional;

public interface QuantifiedIngredientRepository {
    Optional<QuantifiedIngredient> getQuantifiedIngredient(String id);

    QuantifiedIngredient saveQuantifiedIngredient(QuantifiedIngredient quantifiedIngredient);

    boolean exists(String id);
}
