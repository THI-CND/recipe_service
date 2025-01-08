package de.benedikt_schwering.thicnd.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
@AllArgsConstructor
public class AssociatedTags {
    @NonNull
    private List<String> intersection;
    @NonNull
    private List<String> union;
}
