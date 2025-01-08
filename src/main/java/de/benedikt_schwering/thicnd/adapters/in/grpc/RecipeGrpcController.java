package de.benedikt_schwering.thicnd.adapters.in.grpc;

import de.benedikt_schwering.thicnd.adapters.in.grpc.dto.GrpcDtoConverter;
import de.benedikt_schwering.thicnd.domain.RecipeService;
import de.benedikt_schwering.thicnd.stubs.RecipeServiceGrpc;
import de.benedikt_schwering.thicnd.stubs.*;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class RecipeGrpcController extends RecipeServiceGrpc.RecipeServiceImplBase {
    private final RecipeService recipeService;

    public RecipeGrpcController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @Override
    public void getRecipes(Null request, StreamObserver<RecipesResponse> responseStreamObserver) {
        responseStreamObserver.onNext(GrpcDtoConverter.toRecipesResponse(recipeService.getRecipes()));
        responseStreamObserver.onCompleted();
    }

    @Override
    public void getRecipe(RecipeIdRequest request, StreamObserver<RecipeResponse> responseStreamObserver) {
        var recipe = recipeService.getRecipe(request.getId());

        if (recipe.isPresent()) {
            responseStreamObserver.onNext(GrpcDtoConverter.toRecipeResponse(recipe.get()));
            responseStreamObserver.onCompleted();
        } else {
            responseStreamObserver.onError(Status.NOT_FOUND.withDescription("Recipe not found").asException());
        }
    }

    @Override
    public void getTotalIngredients(RecipeIdRequest request, StreamObserver<TotalIngredientsResponse> responseStreamObserver) {
        var recipe = recipeService.getRecipe(request.getId());

        if (recipe.isPresent()) {
            var totalIngredients = recipeService.getTotalIngredients(recipe.get());

            responseStreamObserver.onNext(GrpcDtoConverter.toTotalIngredientsResponse(totalIngredients));
            responseStreamObserver.onCompleted();
        } else {
            responseStreamObserver.onError(Status.NOT_FOUND.withDescription("Recipe not found").asException());
        }
    }

    @Override
    public void getAssociatedTags(RecipeIdRequest request, StreamObserver<AssociatedTagsResponse> responseStreamObserver) {
        var recipe = recipeService.getRecipe(request.getId());

        if (recipe.isPresent()) {
            var associatedTags = recipeService.getAssociatedTags(recipe.get());

            responseStreamObserver.onNext(GrpcDtoConverter.toAssociatedTagsResponse(associatedTags));
            responseStreamObserver.onCompleted();
        } else {
            responseStreamObserver.onError(Status.NOT_FOUND.withDescription("Recipe not found").asException());
        }
    }

    @Override
    public void createRecipe(CreateRecipeRequest request, StreamObserver<RecipeResponse> responseStreamObserver) {
        var recipe = recipeService.createRecipe(GrpcDtoConverter.toRecipe(request.getRecipe()));

        responseStreamObserver.onNext(GrpcDtoConverter.toRecipeResponse(recipe));
        responseStreamObserver.onCompleted();
    }

    @Override
    public void updateRecipe(UpdateRecipeRequest request, StreamObserver<RecipeResponse> responseStreamObserver) {
        var recipe = recipeService.updateRecipe(
                request.getId(),
                GrpcDtoConverter.toRecipe(request.getRecipe())
        );

        if (recipe.isPresent()) {
            responseStreamObserver.onNext(GrpcDtoConverter.toRecipeResponse(recipe.get()));
            responseStreamObserver.onCompleted();
        } else {
            responseStreamObserver.onError(Status.NOT_FOUND.withDescription("Recipe not found").asException());
        }
    }

    @Override
    public void deleteRecipe(RecipeIdRequest request, StreamObserver<Null> responseStreamObserver) {
        recipeService.deleteRecipe(request.getId());

        responseStreamObserver.onNext(Null.getDefaultInstance());
        responseStreamObserver.onCompleted();
    }
}
