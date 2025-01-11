package de.benedikt_schwering.thicnd.adapters.out.redis.dto;

import de.benedikt_schwering.thicnd.domain.model.Ingredient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@RedisHash("Ingredient")
public class IngredientCache implements Serializable {
    private long id;
    @NonNull
    private List<String> tags;

    public static IngredientCache fromIngredient(Ingredient ingredient) {
        return new IngredientCache(
                ingredient.getId(),
                ingredient.getTags()
        );
    }

    public Ingredient toIngredient() {
        return new Ingredient(
                this.id,
                this.tags
        );
    }
}
