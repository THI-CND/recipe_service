package de.benedikt_schwering.thicnd.application;

import de.benedikt_schwering.thicnd.domain.QuantifiedIngredientService;
import de.benedikt_schwering.thicnd.domain.model.QuantifiedIngredient;
import de.benedikt_schwering.thicnd.ports.out.QuantifiedIngredientRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuantifiedIngredientServiceImpl implements QuantifiedIngredientService {
    private final QuantifiedIngredientRepository quantifiedIngredientRepository;

    public QuantifiedIngredientServiceImpl(QuantifiedIngredientRepository quantifiedIngredientRepository) {
        this.quantifiedIngredientRepository = quantifiedIngredientRepository;
    }

    @Override
    public Optional<QuantifiedIngredient> getQuantifiedIngredient(String id) {
        return quantifiedIngredientRepository.getQuantifiedIngredient(id);
    }

    @Override
    public Optional<QuantifiedIngredient> updateQuantifiedIngredient(String id, QuantifiedIngredient quantifiedIngredient) {
        if (quantifiedIngredientRepository.exists(id)) {
            quantifiedIngredient.setId(id);

            return Optional.of(quantifiedIngredientRepository.saveQuantifiedIngredient(quantifiedIngredient));
        }

        return Optional.empty();
    }
}
