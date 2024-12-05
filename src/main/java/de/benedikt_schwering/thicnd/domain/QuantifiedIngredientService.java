package de.benedikt_schwering.thicnd.domain;

import de.benedikt_schwering.thicnd.domain.model.QuantifiedIngredient;

import java.util.Optional;

public interface QuantifiedIngredientService {
    Optional<QuantifiedIngredient> getQuantifiedIngredient(String id);

    Optional<QuantifiedIngredient> updateQuantifiedIngredient(String id, QuantifiedIngredient quantifiedIngredient);
}
