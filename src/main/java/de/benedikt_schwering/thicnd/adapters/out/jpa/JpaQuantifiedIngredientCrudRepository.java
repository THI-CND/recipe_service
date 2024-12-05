package de.benedikt_schwering.thicnd.adapters.out.jpa;

import de.benedikt_schwering.thicnd.adapters.out.jpa.entity.QuantifiedIngredientEntity;
import org.springframework.data.repository.CrudRepository;

public interface JpaQuantifiedIngredientCrudRepository extends CrudRepository<QuantifiedIngredientEntity, String> {
}
