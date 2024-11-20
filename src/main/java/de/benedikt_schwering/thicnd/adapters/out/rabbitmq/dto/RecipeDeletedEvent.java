package de.benedikt_schwering.thicnd.adapters.out.rabbitmq.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecipeDeletedEvent {
    private String id;

    public static RecipeDeletedEvent fromId(String id) {
        return new RecipeDeletedEvent(id);
    }
}
