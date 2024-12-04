package de.benedikt_schwering.thicnd.domain;

import de.benedikt_schwering.thicnd.domain.model.QuantifiedIngredient;
import de.benedikt_schwering.thicnd.domain.model.Step;

import java.util.Optional;

public interface StepService {
    Optional<Step> getStep(String id);

    Optional<Step> updateStep(String id, Step step);

    Optional<Step> addQuantifiedIngredientToStep(String id, QuantifiedIngredient quantifiedIngredient);
}
