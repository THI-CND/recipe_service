package de.benedikt_schwering.thicnd.ports.out;

import de.benedikt_schwering.thicnd.domain.model.Step;

import java.util.Optional;

public interface StepRepository {
    Optional<Step> getStep(String id);

    Step saveStep(Step recipe);

    boolean exists(String id);
}
