package de.benedikt_schwering.thicnd.ports.out;

import de.benedikt_schwering.thicnd.domain.model.Ingredient;

import java.util.Optional;

public interface IngredientCacheProvider {
    void cacheIngredient(Ingredient ingredient);
    Optional<Ingredient> getCachedIngredient(long id);
}
