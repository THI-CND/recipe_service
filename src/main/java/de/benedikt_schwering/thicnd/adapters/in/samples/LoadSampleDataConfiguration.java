package de.benedikt_schwering.thicnd.adapters.in.samples;

import de.benedikt_schwering.thicnd.domain.RecipeService;
import de.benedikt_schwering.thicnd.domain.model.QuantifiedIngredient;
import de.benedikt_schwering.thicnd.domain.model.Recipe;
import de.benedikt_schwering.thicnd.domain.model.Step;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import java.util.List;

@Configuration
@Profile({"development"})
public class LoadSampleDataConfiguration {
    private final RecipeService recipeService;

    Recipe recipe1 = new Recipe(
            "Spaghetti Carbonara",
            "Benedikt",
            "Spaghetti Carbonara is a classic Italian pasta dish that is quick and easy to make. The creamy sauce is made with pancetta, eggs, and Parmesan cheese. This recipe uses spaghetti, but you can use any pasta you like.",
            List.of(
                    new Step(
                            List.of(
                                    new QuantifiedIngredient(
                                            1,
                                            200
                                    )
                            ),
                            "Cook the spaghetti according to the package instructions."
                    ),
                    new Step(
                            List.of(
                                    new QuantifiedIngredient(
                                            2,
                                            100
                                    ),
                                    new QuantifiedIngredient(
                                            1,
                                            1
                                    )
                            ),
                            "In a large skillet, cook the pancetta in olive oil over medium heat until crispy."
                    )
            )
    );

    public LoadSampleDataConfiguration(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @Bean
    public CommandLineRunner loadData() {
        return args -> {
            recipeService.createRecipe(recipe1);
        };
    }
}
