package de.benedikt_schwering.thicnd.adapters.out.rabbitmq.dto;

import de.benedikt_schwering.thicnd.domain.model.Step;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class StepEvent {
    private String id;
    private List<QuantifiedIngredientEvent> quantifiedIngredients;
    private String description;

    public static StepEvent fromStep(Step step) {
        return new StepEvent(
                step.getId(),
                step.getQuantifiedIngredients().stream().map(QuantifiedIngredientEvent::fromQuantifiedIngredient).toList(),
                step.getDescription()
        );
    }
}
