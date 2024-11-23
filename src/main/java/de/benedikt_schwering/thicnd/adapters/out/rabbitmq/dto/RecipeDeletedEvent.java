package de.benedikt_schwering.thicnd.adapters.out.rabbitmq.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecipeDeletedEvent {
    private String id;

    public static RecipeDeletedEvent fromId(String id) {
        return new RecipeDeletedEvent(id);
    }

    public String toJSON() {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
