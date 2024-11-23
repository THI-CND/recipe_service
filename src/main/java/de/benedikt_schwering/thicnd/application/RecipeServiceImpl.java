package de.benedikt_schwering.thicnd.application;

import de.benedikt_schwering.thicnd.domain.RecipeService;
import de.benedikt_schwering.thicnd.domain.model.QuantifiedIngredient;
import de.benedikt_schwering.thicnd.domain.model.Recipe;
import de.benedikt_schwering.thicnd.ports.out.RecipeEvents;
import de.benedikt_schwering.thicnd.ports.out.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final RecipeEvents recipeEvents;

    RecipeServiceImpl(RecipeRepository recipeRepository, RecipeEvents recipeEvents) {
        this.recipeRepository = recipeRepository;
        this.recipeEvents = recipeEvents;
    }

    @Override
    public List<Recipe> getRecipes() {
        return recipeRepository.getRecipes();
    }

    @Override
    public Optional<Recipe> getRecipe(String id) {
        return recipeRepository.getRecipe(id);
    }

    @Override
    public Recipe createRecipe(Recipe recipe) {
        var savedRecipe = recipeRepository.saveRecipe(recipe);
        recipeEvents.recipeCreated(savedRecipe);

        return savedRecipe;
    }

    @Override
    public Optional<Recipe> updateRecipe(String id, Recipe recipe) {
        if (recipeRepository.exists(id)) {
            recipe.setId(id);

            var savedRecipe = recipeRepository.saveRecipe(recipe);
            recipeEvents.recipeUpdated(savedRecipe);

            return Optional.of(savedRecipe);
        }

        return Optional.empty();
    }

    @Override
    public void deleteRecipe(String id) {
        recipeRepository.deleteRecipe(id);
        recipeEvents.recipeDeleted(id);
    }

    @Override
    public List<QuantifiedIngredient> getTotalIngredients(String id) {
        return List.of();
    }
}
