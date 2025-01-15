package de.benedikt_schwering.thicnd.adapters.in.rest;

import de.benedikt_schwering.thicnd.adapters.in.rest.dto.RecipeResponse;
import de.benedikt_schwering.thicnd.adapters.in.rest.dto.StepRequest;
import de.benedikt_schwering.thicnd.domain.RecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v2/recipe")
public class RestV2RecipeController {
    private final RecipeService recipeService;

    public RestV2RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PatchMapping("/{id}/step")
    public RecipeResponse addStepToRecipe(@PathVariable String id, @RequestBody StepRequest stepRequest) {
        var recipe = recipeService.addStepToRecipe(
                id,
                stepRequest.toStep()
        );

        if (recipe.isPresent())
            return RecipeResponse.fromRecipe(recipe.get());

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found");
    }

    @DeleteMapping("/{id}/step/{stepId}")
    public RecipeResponse deleteStepFromRecipe(@PathVariable String id, @PathVariable String stepId) {
        var recipe = recipeService.deleteStepFromRecipe(
                id,
                stepId
        );

        if (recipe.isPresent())
            return RecipeResponse.fromRecipe(recipe.get());

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found");
    }
}
