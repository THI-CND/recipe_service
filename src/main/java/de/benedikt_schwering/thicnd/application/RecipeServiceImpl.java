package de.benedikt_schwering.thicnd.application;

import de.benedikt_schwering.thicnd.domain.RecipeService;
import de.benedikt_schwering.thicnd.domain.model.QuantifiedIngredient;
import de.benedikt_schwering.thicnd.domain.model.Recipe;
import de.benedikt_schwering.thicnd.ports.out.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;

    RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
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
        return recipeRepository.saveRecipe(recipe);
    }

    @Override
    public Optional<Recipe> updateRecipe(String id, Recipe recipe) {
        if (recipeRepository.exists(id)) {
            recipe.setId(id);
            return Optional.of(recipeRepository.saveRecipe(recipe));
        }

        return Optional.empty();
    }

    @Override
    public void deleteRecipe(String id) {
        recipeRepository.deleteRecipe(id);
    }

    @Override
    public List<QuantifiedIngredient> getTotalIngredients(String id) {
        return List.of();
    }
}
