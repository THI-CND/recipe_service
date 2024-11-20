package de.benedikt_schwering.thicnd.domain.model;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Step {
    private String id;
    @NonNull
    private List<QuantifiedIngredient> quantifiedIngredients;
    @NonNull
    private String description;
}
