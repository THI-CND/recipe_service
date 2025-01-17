package de.benedikt_schwering.thicnd.application;

import de.benedikt_schwering.thicnd.domain.IngredientService;
import de.benedikt_schwering.thicnd.domain.RecipeService;
import de.benedikt_schwering.thicnd.domain.model.AssociatedTags;
import de.benedikt_schwering.thicnd.domain.model.QuantifiedIngredient;
import de.benedikt_schwering.thicnd.domain.model.Recipe;
import de.benedikt_schwering.thicnd.domain.model.Step;
import de.benedikt_schwering.thicnd.ports.out.RecipeEvents;
import de.benedikt_schwering.thicnd.ports.out.RecipeRepository;
import io.grpc.StatusRuntimeException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final RecipeEvents recipeEvents;
    private final IngredientService ingredientService;

    RecipeServiceImpl(RecipeRepository recipeRepository, RecipeEvents recipeEvents, IngredientService ingredientService) {
        this.recipeRepository = recipeRepository;
        this.recipeEvents = recipeEvents;
        this.ingredientService = ingredientService;
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
    public List<QuantifiedIngredient> getTotalIngredients(Recipe recipe) {
        Map<Long, Double> totalIngredients = new HashMap<Long, Double>();

        for (var step : recipe.getSteps())
            for (var quantifiedIngredient : step.getQuantifiedIngredients())
                totalIngredients.merge(quantifiedIngredient.getIngredient(), quantifiedIngredient.getQuantity(), Double::sum);

        return totalIngredients.entrySet().stream().map(entry -> {
            return new QuantifiedIngredient(entry.getKey(), entry.getValue());
        }).toList();
    }

    @Override
    public AssociatedTags getAssociatedTags(Recipe recipe) {
        var totalIngredients = getTotalIngredients(recipe);

        Set<String> intersection = new HashSet<String>();
        Set<String> union = new HashSet<String>();

        for (int i = 0; i < totalIngredients.size(); i++) {
            try {
                var ingredient = ingredientService.getIngredient(totalIngredients.get(i).getIngredient());

                union.addAll(ingredient.getTags());

                if (i == 0)
                    intersection.addAll(ingredient.getTags());
                else
                    intersection.retainAll(ingredient.getTags());
            } catch (StatusRuntimeException e) {
                continue;
            }
        }

        return new AssociatedTags(
                intersection.stream().toList(),
                union.stream().toList()
        );
    }

    @Override
    public Optional<Recipe> addStepToRecipe(String id, Step step) {
        var recipe = recipeRepository.getRecipe(id);

        if (recipe.isPresent()) {
            var updatedRecipe = recipe.get();

            var steps = new ArrayList<Step>(updatedRecipe.getSteps());
            steps.add(step);

            updatedRecipe.setSteps(steps);

            var savedRecipe = recipeRepository.saveRecipe(updatedRecipe);
            recipeEvents.recipeUpdated(savedRecipe);

            return Optional.of(savedRecipe);
        }

        return Optional.empty();
    }

    @Override
    public Optional<Recipe> deleteStepFromRecipe(String id, String stepId) {
        var recipe = recipeRepository.getRecipe(id);

        if (recipe.isPresent()) {
            var updatedRecipe = recipe.get();

            var steps = new ArrayList<Step>(updatedRecipe.getSteps());
            steps.removeIf(step -> step.getId().equals(stepId));

            updatedRecipe.setSteps(steps);

            var savedRecipe = recipeRepository.saveRecipe(updatedRecipe);
            recipeEvents.recipeUpdated(savedRecipe);

            return Optional.of(savedRecipe);
        }

        return Optional.empty();
    }
}
