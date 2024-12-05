package de.benedikt_schwering.thicnd.adapters.in.rest;

import de.benedikt_schwering.thicnd.adapters.in.rest.dto.QuantifiedIngredientRequest;
import de.benedikt_schwering.thicnd.adapters.in.rest.dto.QuantifiedIngredientResponse;
import de.benedikt_schwering.thicnd.domain.QuantifiedIngredientService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/quantified-ingredient")
public class QuantifiedIngredientRestV1Controller {
    private final QuantifiedIngredientService quantifiedIngredientService;

    public QuantifiedIngredientRestV1Controller(QuantifiedIngredientService quantifiedIngredientService) {
        this.quantifiedIngredientService = quantifiedIngredientService;
    }

    @GetMapping("/{id}")
    public QuantifiedIngredientResponse getQuantifiedIngredient(@PathVariable String id) {
        var quantifiedIngredient = quantifiedIngredientService.getQuantifiedIngredient(id);

        if (quantifiedIngredient.isPresent())
            return QuantifiedIngredientResponse.fromQuantifiedIngredient(quantifiedIngredient.get());

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Quantified Ingredient not found");
    }

    @PutMapping("/{id}")
    public QuantifiedIngredientResponse updateQuantifiedIngredient(@PathVariable String id, @RequestBody QuantifiedIngredientRequest quantifiedIngredientRequest) {
        var quantifiedIngredient = quantifiedIngredientService.updateQuantifiedIngredient(
                id,
                quantifiedIngredientRequest.toQuantifiedIngredient()
        );

        if (quantifiedIngredient.isPresent())
            return QuantifiedIngredientResponse.fromQuantifiedIngredient(quantifiedIngredient.get());

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Quantified Ingredient not found");
    }
}
