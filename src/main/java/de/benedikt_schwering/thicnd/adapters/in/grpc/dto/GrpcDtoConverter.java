package de.benedikt_schwering.thicnd.adapters.in.grpc.dto;

import de.benedikt_schwering.thicnd.domain.model.AssociatedTags;
import de.benedikt_schwering.thicnd.domain.model.QuantifiedIngredient;
import de.benedikt_schwering.thicnd.domain.model.Recipe;
import de.benedikt_schwering.thicnd.domain.model.Step;
import de.benedikt_schwering.thicnd.stubs.*;

import java.util.List;

public class GrpcDtoConverter {
    public static Recipe toRecipe(RecipeRequest recipeRequest) {
        return new Recipe(
                recipeRequest.getName(),
                recipeRequest.getDescription(),
                toSteps(recipeRequest.getStepsList())
        );
    }

    private static List<Step> toSteps(List<StepRequest> stepRequests) {
        return stepRequests
                .stream()
                .map(stepRequest ->
                        new Step(
                                toQuantifiedIngredients(stepRequest.getQuantifiedIngredientsList()),
                                stepRequest.getDescription()
                        )
                )
                .toList();
    }

    private static List<QuantifiedIngredient> toQuantifiedIngredients(List<QuantifiedIngredientRequest> quantifiedIngredientRequests) {
        return quantifiedIngredientRequests
                .stream()
                .map(quantifiedIngredientRequest ->
                        new QuantifiedIngredient(
                                quantifiedIngredientRequest.getIngredient(),
                                quantifiedIngredientRequest.getQuantity()
                        )
                )
                .toList();
    }

    public static RecipesResponse toRecipesResponse(List<Recipe> recipes) {
        return RecipesResponse.newBuilder()
                .addAllRecipes(
                        recipes
                                .stream()
                                .map(GrpcDtoConverter::toRecipeResponse)
                                .toList()
                ).build();
    }

    public static RecipeResponse toRecipeResponse(Recipe recipe) {
        return RecipeResponse.newBuilder()
                .setId(recipe.getId())
                .setName(recipe.getName())
                .setDescription(recipe.getDescription())
                .addAllSteps(toStepResponses(recipe.getSteps()))
                .build();
    }

    private static List<StepResponse> toStepResponses(List<Step> steps) {
        return steps
                .stream()
                .map(step ->
                        StepResponse.newBuilder()
                                .setId(step.getId())
                                .addAllQuantifiedIngredients(toQuantifiedIngredientResponses(step.getQuantifiedIngredients()))
                                .setDescription(step.getDescription())
                                .build()
                )
                .toList();
    }

    private static List<QuantifiedIngredientResponse> toQuantifiedIngredientResponses(List<QuantifiedIngredient> quantifiedIngredients) {
        return quantifiedIngredients
                .stream()
                .map(quantifiedIngredient ->
                        QuantifiedIngredientResponse.newBuilder()
                                .setId(quantifiedIngredient.getId())
                                .setIngredient(quantifiedIngredient.getIngredient())
                                .setQuantity(quantifiedIngredient.getQuantity())
                                .build()
                )
                .toList();
    }

    public static TotalIngredientsResponse toTotalIngredientsResponse(List<QuantifiedIngredient> quantifiedIngredients) {
        return TotalIngredientsResponse.newBuilder()
                .addAllTotalIngredients(
                        quantifiedIngredients
                                .stream()
                                .map(GrpcDtoConverter::toTotalIngredientResponse)
                                .toList()
                ).build();
    }

    private static TotalIngredientResponse toTotalIngredientResponse(QuantifiedIngredient quantifiedIngredient) {
        return TotalIngredientResponse.newBuilder()
                .setIngredient(quantifiedIngredient.getIngredient())
                .setQuantity(quantifiedIngredient.getQuantity())
                .build();
    }

    public static AssociatedTagsResponse toAssociatedTagsResponse(AssociatedTags associatedTags) {
        return AssociatedTagsResponse.newBuilder()
                .addAllIntersection(associatedTags.getIntersection())
                .addAllUnion(associatedTags.getUnion())
                .build();
    }
}
