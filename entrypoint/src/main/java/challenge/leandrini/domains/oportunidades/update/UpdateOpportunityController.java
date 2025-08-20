package challenge.leandrini.domains.oportunidades.update;

import challenge.leandrini.domains.oportunidades.models.request.UpdateOpportunityRequest;
import challenge.leandrini.domains.oportunidades.models.response.UpdateOpportunityResponse;
import challenge.leandrini.domains.oportunidades.usecases.update.IUpdateOpportunityUseCase;
import challenge.leandrini.domains.oportunidades.usecases.update.UpdateOpportunityParameters;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/opportunities")
public class UpdateOpportunityController implements UpdateOpportunityApiMethod {

    private final IUpdateOpportunityUseCase updateOpportunityUseCase;

    @Override
    @PutMapping(path = "/{opportunityId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER', 'MANAGER', 'ASSISTANT')")
    public UpdateOpportunityResponse execute(@PathVariable String opportunityId,
                                         @RequestBody @Valid @Parameter(name = "request") UpdateOpportunityRequest request) {
        var parameters = new UpdateOpportunityParameters(
                opportunityId,
                request.getStatus(),
                request.getConclusionReason(),
                request.getAssignedUserId(),
                request.getClientName(),
                request.getClientEmail(),
                request.getClientPhone(),
                request.getVehicleBrand(),
                request.getVehicleModel(),
                request.getVehicleVersion(),
                request.getVehicleYear()
        );

        var opportunity = updateOpportunityUseCase.execute(parameters);

        return new UpdateOpportunityResponse(
                opportunity.getId(),
                opportunity.getCode(),
                opportunity.getDealershipId(),
                opportunity.getStatus().getValue(),
                opportunity.getConclusionReason(),
                opportunity.getAssignedUserId(),
                opportunity.getAssignedAt(),
                opportunity.getConcludedAt(),
                opportunity.getClientName(),
                opportunity.getClientEmail(),
                opportunity.getClientPhone(),
                opportunity.getVehicleBrand(),
                opportunity.getVehicleModel(),
                opportunity.getVehicleVersion(),
                opportunity.getVehicleYear(),
                opportunity.getCreatedAt(),
                opportunity.getUpdatedAt()
        );
    }
}