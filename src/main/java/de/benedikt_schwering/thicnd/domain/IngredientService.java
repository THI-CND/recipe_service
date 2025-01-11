package de.benedikt_schwering.thicnd.domain;

import de.benedikt_schwering.thicnd.domain.model.Ingredient;

public interface IngredientService {
    Ingredient getIngredient(long id);
    Ingredient getIngredient(long id, boolean omitCache);
}
