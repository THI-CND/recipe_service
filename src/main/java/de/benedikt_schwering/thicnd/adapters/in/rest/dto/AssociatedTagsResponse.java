package de.benedikt_schwering.thicnd.adapters.in.rest.dto;

import de.benedikt_schwering.thicnd.domain.model.AssociatedTags;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
@AllArgsConstructor
public class AssociatedTagsResponse {
    @NonNull
    private List<String> intersection;
    @NonNull
    private List<String> union;

    public static AssociatedTagsResponse fromAssociatedTags(AssociatedTags associatedTags) {
        return new AssociatedTagsResponse(
                associatedTags.getIntersection(),
                associatedTags.getUnion()
        );
    }
}
