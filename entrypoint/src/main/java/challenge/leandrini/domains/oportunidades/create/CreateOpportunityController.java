package challenge.leandrini.domains.oportunidades.create;

import challenge.leandrini.domains.oportunidades.models.request.CreateOpportunityRequest;
import challenge.leandrini.domains.oportunidades.usecases.create.CreateOpportunityParameters;
import challenge.leandrini.domains.oportunidades.usecases.create.ICreateOpportunityUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/opportunities")
public class CreateOpportunityController implements CreateOpportunityApiMethod {

    private final ICreateOpportunityUseCase createOpportunityUseCase;

    @Override
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN') or hasRole('OWNER') or hasRole('MANAGER')")
    public void execute(@RequestBody @Valid CreateOpportunityRequest request) {

        var parameters = new CreateOpportunityParameters(
                request.getDealershipId(),
                request.getClientName(),
                request.getClientEmail(),
                request.getClientPhone(),
                request.getVehicleBrand(),
                request.getVehicleModel(),
                request.getVehicleVersion(),
                request.getVehicleYear()
        );

        createOpportunityUseCase.execute(parameters);
    }
}