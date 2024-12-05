package de.benedikt_schwering.thicnd.application;

import de.benedikt_schwering.thicnd.domain.StepService;
import de.benedikt_schwering.thicnd.domain.model.QuantifiedIngredient;
import de.benedikt_schwering.thicnd.domain.model.Step;
import de.benedikt_schwering.thicnd.ports.out.StepRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class StepServiceImpl implements StepService {
    private final StepRepository stepRepository;

    StepServiceImpl(StepRepository stepRepository) {
        this.stepRepository = stepRepository;
    }

    @Override
    public Optional<Step> getStep(String id) {
        return stepRepository.getStep(id);
    }

    @Override
    public Optional<Step> updateStep(String id, Step step) {
        if (stepRepository.exists(id)) {
            step.setId(id);

            return Optional.of(stepRepository.saveStep(step));
        }

        return Optional.empty();
    }

    @Override
    public Optional<Step> addQuantifiedIngredientToStep(String id, QuantifiedIngredient quantifiedIngredient) {
        var step = stepRepository.getStep(id);

        if (step.isPresent()) {
            var updatedStep = step.get();

            var quantifiedIngredients = new ArrayList<QuantifiedIngredient>(updatedStep.getQuantifiedIngredients());
            quantifiedIngredients.add(quantifiedIngredient);

            updatedStep.setQuantifiedIngredients(quantifiedIngredients);

            return Optional.of(stepRepository.saveStep(updatedStep));
        }

        return Optional.empty();
    }

    @Override
    public Optional<Step> deleteQuantifiedIngredientFromStep(String id, String quantifiedIngredientId) {
        var step = stepRepository.getStep(id);

        if (step.isPresent()) {
            var updatedStep = step.get();

            var quantifiedIngredients = new ArrayList<QuantifiedIngredient>(updatedStep.getQuantifiedIngredients());
            quantifiedIngredients.removeIf(quantifiedIngredient -> quantifiedIngredient.getId().equals(quantifiedIngredientId));

            updatedStep.setQuantifiedIngredients(quantifiedIngredients);

            return Optional.of(stepRepository.saveStep(updatedStep));
        }

        return Optional.empty();
    }
}
