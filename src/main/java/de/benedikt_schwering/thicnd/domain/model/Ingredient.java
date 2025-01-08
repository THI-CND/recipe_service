package de.benedikt_schwering.thicnd.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
@AllArgsConstructor
public class Ingredient {
    private long id;
    @NonNull
    private List<String> tags;
}
