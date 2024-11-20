package de.benedikt_schwering.thicnd.adapters.out.jpa;

import de.benedikt_schwering.thicnd.adapters.out.jpa.entity.RecipeEntity;
import de.benedikt_schwering.thicnd.domain.model.Recipe;
import de.benedikt_schwering.thicnd.ports.out.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeRepositoryImpl implements RecipeRepository {
    private final JpaRecipeCrudRepository jpaRecipeRepository;

    RecipeRepositoryImpl(JpaRecipeCrudRepository jpaRecipeRepository) {
        this.jpaRecipeRepository = jpaRecipeRepository;
    }

    @Override
    public Optional<Recipe> getRecipe(String id) {
        return jpaRecipeRepository.findById(id).map(RecipeEntity::toRecipe);
    }

    @Override
    public List<Recipe> getRecipes() {
        return jpaRecipeRepository.findAll().stream().map(RecipeEntity::toRecipe).toList();
    }

    @Override
    public Recipe saveRecipe(Recipe recipe) {
        return jpaRecipeRepository.save(RecipeEntity.fromRecipe(recipe)).toRecipe();
    }

    @Override
    public void deleteRecipe(String id) {
        jpaRecipeRepository.deleteById(id);
    }

    @Override
    public boolean exists(String id) {
        return jpaRecipeRepository.existsById(id);
    }
}
