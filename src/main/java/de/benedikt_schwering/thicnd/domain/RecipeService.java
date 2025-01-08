package de.benedikt_schwering.thicnd.domain;

import de.benedikt_schwering.thicnd.domain.model.AssociatedTags;
import de.benedikt_schwering.thicnd.domain.model.QuantifiedIngredient;
import de.benedikt_schwering.thicnd.domain.model.Recipe;
import de.benedikt_schwering.thicnd.domain.model.Step;

import java.util.List;
import java.util.Optional;

public interface RecipeService {
    List<Recipe> getRecipes();

    Optional<Recipe> getRecipe(String id);

    Recipe createRecipe(Recipe recipe);

    Optional<Recipe> updateRecipe(String id, Recipe recipe);

    void deleteRecipe(String id);

    List<QuantifiedIngredient> getTotalIngredients(Recipe recipe);

    AssociatedTags getAssociatedTags(Recipe recipe);

    Optional<Recipe> addStepToRecipe(String id, Step step);

    Optional<Recipe> deleteStepFromRecipe(String id, String stepId);
}
