package de.benedikt_schwering.thicnd.adapters.out.jpa.entity;

import de.benedikt_schwering.thicnd.domain.model.Step;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StepEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuantifiedIngredientEntity> quantifiedIngredients;
    private String description;

    public static StepEntity fromStep(Step step) {
        return new StepEntity(
                step.getId(),
                step.getQuantifiedIngredients().stream().map(QuantifiedIngredientEntity::fromQuantifiedIngredient).toList(),
                step.getDescription()
        );
    }

    public Step toStep() {
        return new Step(
                getId(),
                getQuantifiedIngredients().stream().map(QuantifiedIngredientEntity::toQuantifiedIngredient).toList(),
                getDescription()
        );
    }
}
