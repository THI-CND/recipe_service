package de.benedikt_schwering.thicnd.adapters.out.jpa;

import de.benedikt_schwering.thicnd.adapters.out.jpa.entity.StepEntity;
import de.benedikt_schwering.thicnd.domain.model.Step;
import de.benedikt_schwering.thicnd.ports.out.StepRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JpaStepRepositoryImpl implements StepRepository {
    private final JpaStepCrudRepository jpaStepRepository;

    public JpaStepRepositoryImpl(JpaStepCrudRepository jpaStepRepository) {
        this.jpaStepRepository = jpaStepRepository;
    }

    @Override
    public Optional<Step> getStep(String id) {
        return jpaStepRepository.findById(id).map(StepEntity::toStep);
    }

    @Override
    public Step saveStep(Step recipe) {
        return jpaStepRepository.save(StepEntity.fromStep(recipe)).toStep();
    }

    @Override
    public boolean exists(String id) {
        return jpaStepRepository.existsById(id);
    }
}
