package challenge.leandrini.domains.oportunidades.create;

import challenge.leandrini.domains.oportunidades.models.request.CreateOpportunityRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Opportunities")
@FunctionalInterface
public interface CreateOpportunityApiMethod {
    
    @Operation(summary = "Create a new opportunity")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Opportunity created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    void execute(@RequestBody @Valid CreateOpportunityRequest request);
}