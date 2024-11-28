package de.benedikt_schwering.thicnd.adapters.in.rest;

import de.benedikt_schwering.thicnd.adapters.in.rest.dto.QuantifiedIngredientResponse;
import de.benedikt_schwering.thicnd.adapters.in.rest.dto.RecipeRequest;
import de.benedikt_schwering.thicnd.adapters.in.rest.dto.RecipeResponse;
import de.benedikt_schwering.thicnd.adapters.in.rest.dto.TotalIngredientResponse;
import de.benedikt_schwering.thicnd.domain.RecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/recipe")
public class RecipeRestController {
    private final RecipeService recipeService;

    public RecipeRestController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping()
    public List<RecipeResponse> getRecipes() {
        return recipeService.getRecipes().stream().map(RecipeResponse::fromRecipe).toList();
    }

    @GetMapping("/{id}")
    public RecipeResponse getRecipe(@PathVariable String id) {
        var recipe = recipeService.getRecipe(id);

        if (recipe.isPresent())
            return RecipeResponse.fromRecipe(recipe.get());

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found");
    }

    @GetMapping("/{id}/total-ingredients")
    public List<TotalIngredientResponse> getTotalIngredients(@PathVariable String id) {
        var recipe = recipeService.getRecipe(id);

        if (recipe.isPresent())
            return recipeService.getTotalIngredients(recipe.get()).stream().map(TotalIngredientResponse::fromQuantifiedIngredient).toList();

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found");
    }

    @PostMapping()
    public RecipeResponse createRecipe(@RequestBody RecipeRequest recipeRequest) {
        return RecipeResponse.fromRecipe(
                recipeService.createRecipe(
                        recipeRequest.toRecipe()
                )
        );
    }

    @PutMapping("/{id}")
    public RecipeResponse updateRecipe(@PathVariable String id, @RequestBody RecipeRequest recipeRequest) {
        var recipe = recipeService.updateRecipe(
                id,
                recipeRequest.toRecipe()
        );

        if (recipe.isPresent())
            return RecipeResponse.fromRecipe(recipe.get());

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found");
    }

    @DeleteMapping("/{id}")
    public void deleteRecipe(@PathVariable String id) {
        recipeService.deleteRecipe(id);
    }
}
