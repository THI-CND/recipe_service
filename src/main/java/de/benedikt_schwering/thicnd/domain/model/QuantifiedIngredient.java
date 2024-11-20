package de.benedikt_schwering.thicnd.domain.model;

import lombok.*;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class QuantifiedIngredient {
    private String id;
    @NonNull
    private String ingredient;
    @NonNull
    private double quantity;
}
