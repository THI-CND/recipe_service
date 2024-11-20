package de.benedikt_schwering.thicnd.adapters.out.jpa;

import de.benedikt_schwering.thicnd.adapters.out.jpa.entity.RecipeEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface JpaRecipeCrudRepository extends CrudRepository<RecipeEntity, String> {
    public List<RecipeEntity> findAll();
}
