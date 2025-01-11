package de.benedikt_schwering.thicnd.application;

import de.benedikt_schwering.thicnd.domain.IngredientService;
import de.benedikt_schwering.thicnd.domain.model.Ingredient;
import de.benedikt_schwering.thicnd.ports.out.IngredientCacheProvider;
import de.benedikt_schwering.thicnd.ports.out.IngredientProvider;
import org.springframework.stereotype.Service;

@Service
public class IngredientServiceImpl implements IngredientService {
    private final IngredientProvider ingredientProvider;
    private final IngredientCacheProvider ingredientCacheProvider;

    IngredientServiceImpl(IngredientProvider ingredientProvider, IngredientCacheProvider ingredientCacheProvider) {
        this.ingredientProvider = ingredientProvider;
        this.ingredientCacheProvider = ingredientCacheProvider;
    }

    @Override
    public Ingredient getIngredient(long id) {
        return this.getIngredient(id, false);
    }

    @Override
    public Ingredient getIngredient(long id, boolean omitCache) {
        if (omitCache)
            return ingredientProvider.getIngredient(id);

        var cachedIngredient = ingredientCacheProvider.getCachedIngredient(id);

        if (cachedIngredient.isPresent())
            return cachedIngredient.get();

        var ingredient = ingredientProvider.getIngredient(id);
        ingredientCacheProvider.cacheIngredient(ingredient);

        return ingredient;
    }
}
