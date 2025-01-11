package de.benedikt_schwering.thicnd.adapters.out.grpc;

import de.benedikt_schwering.thicnd.domain.model.Ingredient;
import de.benedikt_schwering.thicnd.ports.out.IngredientProvider;
import de.thi.cnd.ingredient.IngredientIdRequest;
import de.thi.cnd.ingredient.IngredientServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class GrpcIngredientProvider implements IngredientProvider {
    @GrpcClient("ingredient-service")
    private IngredientServiceGrpc.IngredientServiceBlockingStub ingredientServiceStub;

    @Override
    public Ingredient getIngredient(long id) {
        var response = ingredientServiceStub.getIngredient(
                IngredientIdRequest.newBuilder()
                        .setId(id)
                        .build()
        );

        return new Ingredient(
                response.getId(),
                response.getTagsList().stream().toList()
        );
    }
}
