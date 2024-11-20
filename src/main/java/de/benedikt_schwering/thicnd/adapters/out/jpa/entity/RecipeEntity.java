package de.benedikt_schwering.thicnd.adapters.out.jpa.entity;

import de.benedikt_schwering.thicnd.domain.model.Recipe;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String description;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StepEntity> steps;

    public static RecipeEntity fromRecipe(Recipe recipe) {
        return new RecipeEntity(
                recipe.getId(),
                recipe.getName(),
                recipe.getDescription(),
                recipe.getSteps().stream().map(StepEntity::fromStep).toList()
        );
    }

    public Recipe toRecipe() {
        return new Recipe(
                getId(),
                getName(),
                getDescription(),
                getSteps().stream().map(StepEntity::toStep).toList()
        );
    }
}
