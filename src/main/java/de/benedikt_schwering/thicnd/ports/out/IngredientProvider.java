package de.benedikt_schwering.thicnd.ports.out;

import de.benedikt_schwering.thicnd.domain.model.Ingredient;

public interface IngredientProvider {
    Ingredient getIngredient(long id);
}
