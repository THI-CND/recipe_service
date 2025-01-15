package de.benedikt_schwering.thicnd.adapters.in.rest;

import de.benedikt_schwering.thicnd.adapters.in.rest.dto.StepRequest;
import de.benedikt_schwering.thicnd.adapters.in.rest.dto.StepResponse;
import de.benedikt_schwering.thicnd.domain.StepService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/step")
public class RestV1StepController {
    private final StepService stepService;

    public RestV1StepController(StepService stepService) {
        this.stepService = stepService;
    }

    @GetMapping("/{id}")
    public StepResponse getStep(@PathVariable String id) {
        var step = stepService.getStep(id);

        if (step.isPresent())
            return StepResponse.fromStep(step.get());

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Step not found");
    }

    @PutMapping("/{id}")
    public StepResponse updateStep(@PathVariable String id, @RequestBody StepRequest stepRequest) {
        var step = stepService.updateStep(
                id,
                stepRequest.toStep()
        );

        if (step.isPresent())
            return StepResponse.fromStep(step.get());

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Step not found");
    }
}
