package de.benedikt_schwering.thicnd.adapters.out.jpa;

import de.benedikt_schwering.thicnd.adapters.out.jpa.entity.QuantifiedIngredientEntity;
import de.benedikt_schwering.thicnd.domain.model.QuantifiedIngredient;
import de.benedikt_schwering.thicnd.ports.out.QuantifiedIngredientRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JpaQuantifiedIngredientRepositoryImpl implements QuantifiedIngredientRepository {
    private final JpaQuantifiedIngredientCrudRepository jpaQuantifiedIngredientRepository;

    public JpaQuantifiedIngredientRepositoryImpl(JpaQuantifiedIngredientCrudRepository jpaQuantifiedIngredientRepository) {
        this.jpaQuantifiedIngredientRepository = jpaQuantifiedIngredientRepository;
    }

    @Override
    public Optional<QuantifiedIngredient> getQuantifiedIngredient(String id) {
        return jpaQuantifiedIngredientRepository.findById(id).map(QuantifiedIngredientEntity::toQuantifiedIngredient);
    }

    @Override
    public QuantifiedIngredient saveQuantifiedIngredient(QuantifiedIngredient quantifiedIngredient) {
        return jpaQuantifiedIngredientRepository.save(QuantifiedIngredientEntity.fromQuantifiedIngredient(quantifiedIngredient)).toQuantifiedIngredient();
    }

    @Override
    public boolean exists(String id) {
        return jpaQuantifiedIngredientRepository.existsById(id);
    }
}
