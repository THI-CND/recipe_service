package de.benedikt_schwering.thicnd.ports.out;

import de.benedikt_schwering.thicnd.domain.model.Recipe;

public interface RecipeEvents {
    void recipeCreated(Recipe recipe);

    void recipeUpdated(Recipe recipe);

    void recipeDeleted(String id);
}
