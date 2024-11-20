package de.benedikt_schwering.thicnd.ports.out;

import de.benedikt_schwering.thicnd.domain.model.Recipe;

import java.util.List;
import java.util.Optional;

public interface RecipeRepository {
    Optional<Recipe> getRecipe(String id);

    List<Recipe> getRecipes();

    Recipe saveRecipe(Recipe recipe);

    void deleteRecipe(String id);

    boolean exists(String id);
}
