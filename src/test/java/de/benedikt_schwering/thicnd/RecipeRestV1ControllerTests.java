package de.benedikt_schwering.thicnd;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.benedikt_schwering.thicnd.adapters.in.rest.dto.QuantifiedIngredientRequest;
import de.benedikt_schwering.thicnd.adapters.in.rest.dto.RecipeRequest;
import de.benedikt_schwering.thicnd.adapters.in.rest.dto.StepRequest;
import de.benedikt_schwering.thicnd.ports.out.RecipeEvents;
import de.benedikt_schwering.thicnd.ports.out.RecipeRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
class RecipeRestV1ControllerTests {
    private final MockMvc mvc;
    private final RecipeRepository recipeRepository;

    @MockBean
    private RecipeEvents recipeEvents;

    @Autowired
    public RecipeRestV1ControllerTests(MockMvc mvc, RecipeRepository recipeRepository) {
        this.mvc = mvc;
        this.recipeRepository = recipeRepository;
    }

    @Test
    @Order(1)
    public void emptyRecipes() throws Exception {
        mvc.perform(
                        get("/api/recipe")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Order(2)
    public void createRecipe() throws Exception {
        var recipe = new RecipeRequest(
                "Spaghetti Carbonara",
                "Spaghetti with bacon, eggs, and cheese",
                List.of(
                        new StepRequest(
                                List.of(
                                        new QuantifiedIngredientRequest(
                                                "Spaghetti",
                                                200
                                        )
                                ),
                                "Cook spaghetti"
                        ),
                        new StepRequest(
                                List.of(
                                        new QuantifiedIngredientRequest(
                                                "Bacon",
                                                100
                                        ),
                                        new QuantifiedIngredientRequest(
                                                "Eggs",
                                                2
                                        ),
                                        new QuantifiedIngredientRequest(
                                                "Parmesan cheese",
                                                50
                                        )
                                ),
                                "Fry bacon, add eggs and cheese"
                        )
                )
        );

        mvc.perform(
                        post("/api/recipe")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(recipe))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Spaghetti Carbonara"))
                .andExpect(jsonPath("$.description").value("Spaghetti with bacon, eggs, and cheese"));
    }

    @Test
    @Order(3)
    public void getRecipes() throws Exception {
        mvc.perform(
                        get("/api/recipe")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$[0].name").value("Spaghetti Carbonara"))
                .andExpect(jsonPath("$[0].description").value("Spaghetti with bacon, eggs, and cheese"));
    }

    @Test
    @Order(4)
    public void getRecipe() throws Exception {
        var recipe = recipeRepository.getRecipes().getFirst();

        mvc.perform(
                        get("/api/recipe/" + recipe.getId())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Spaghetti Carbonara"))
                .andExpect(jsonPath("$.description").value("Spaghetti with bacon, eggs, and cheese"));
    }

    @Test
    @Order(5)
    public void updateRecipe() throws Exception {
        var recipe = recipeRepository.getRecipes().getFirst();
        recipe.setName("Spaghetti Carbonara Updated");
        recipe.setDescription("This was updated!");

        mvc.perform(
                        put("/api/recipe/" + recipe.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(recipe))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Spaghetti Carbonara Updated"))
                .andExpect(jsonPath("$.description").value("This was updated!"));
    }

    @Test
    @Order(6)
    public void deleteRecipe() throws Exception {
        var recipe = recipeRepository.getRecipes().getFirst();

        mvc.perform(
                        delete("/api/recipe/" + recipe.getId())
                )
                .andExpect(status().isOk());

        mvc.perform(
                        get("/api/recipe")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <D> D fromJsonString(final String data, final Class<D> clazz) {
        try {
            return new ObjectMapper().registerModule(new JavaTimeModule()).readValue(data, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
