package de.benedikt_schwering.thicnd.adapters.in.rest;

import de.benedikt_schwering.thicnd.adapters.in.rest.dto.QuantifiedIngredientRequest;
import de.benedikt_schwering.thicnd.adapters.in.rest.dto.StepResponse;
import de.benedikt_schwering.thicnd.domain.StepService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v2/step")
public class StepRestV2Controller {
    private final StepService stepService;

    public StepRestV2Controller(StepService stepService) {
        this.stepService = stepService;
    }

    @PatchMapping("/{id}/quantified-ingredient")
    public StepResponse addQuantifiedIngredientToStep(@PathVariable String id, @RequestBody QuantifiedIngredientRequest quantifiedIngredientRequest) {
        var step = stepService.addQuantifiedIngredientToStep(
                id,
                quantifiedIngredientRequest.toQuantifiedIngredient()
        );

        if (step.isPresent())
            return StepResponse.fromStep(step.get());

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Step not found");
    }

    @DeleteMapping("/{id}/quantified-ingredient/{quantifiedIngredientId}")
    public StepResponse deleteQuantifiedIngredientFromStep(@PathVariable String id, @PathVariable String quantifiedIngredientId) {
        var step = stepService.deleteQuantifiedIngredientFromStep(
                id,
                quantifiedIngredientId
        );

        if (step.isPresent())
            return StepResponse.fromStep(step.get());

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Step not found");
    }
}
