package de.benedikt_schwering.thicnd.domain.model;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Recipe {
    private String id;
    @NonNull
    private String name;
    @NonNull
    private String author;
    @NonNull
    private String description;
    @NonNull
    private List<Step> steps;
}
