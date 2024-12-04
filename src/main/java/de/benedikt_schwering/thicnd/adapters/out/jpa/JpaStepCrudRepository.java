package de.benedikt_schwering.thicnd.adapters.out.jpa;

import de.benedikt_schwering.thicnd.adapters.out.jpa.entity.StepEntity;
import org.springframework.data.repository.CrudRepository;

public interface JpaStepCrudRepository extends CrudRepository<StepEntity, String> {
}
