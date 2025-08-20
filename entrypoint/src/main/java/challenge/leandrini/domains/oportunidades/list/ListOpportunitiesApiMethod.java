package challenge.leandrini.domains.oportunidades.list;

import challenge.leandrini.common.HttpStatusConstants;
import challenge.leandrini.domains.oportunidades.models.request.ListOpportunitiesRequest;
import challenge.leandrini.domains.oportunidades.models.response.ListOpportunitiesResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;

@Tag(name = "Opportunities")
@FunctionalInterface
public interface ListOpportunitiesApiMethod {

    @Operation(summary = "List Opportunities")
    @ApiResponses(value = {@ApiResponse(responseCode = HttpStatusConstants.OK_200, description = HttpStatusConstants.OK_200_MESSAGE),
            @ApiResponse(responseCode = HttpStatusConstants.BAD_REQUEST_400, description = HttpStatusConstants.BAD_REQUEST_400_MESSAGE),
            @ApiResponse(responseCode = HttpStatusConstants.UNAUTHORIZED_401, description = HttpStatusConstants.UNAUTHORIZED_401_MESSAGE),
            @ApiResponse(responseCode = HttpStatusConstants.FORBIDDEN_403, description = HttpStatusConstants.FORBIDDEN_403_MESSAGE),
            @ApiResponse(responseCode = HttpStatusConstants.NOT_FOUND_404, description = HttpStatusConstants.NOT_FOUND_404_MESSAGE),
            @ApiResponse(responseCode = HttpStatusConstants.INTERNAL_SERVER_ERROR_500, description = HttpStatusConstants.INTERNAL_SERVER_ERROR_500_MESSAGE)
    })

    ListOpportunitiesResponse execute(Pageable pageable, @Parameter(hidden = true) ListOpportunitiesRequest request);

}