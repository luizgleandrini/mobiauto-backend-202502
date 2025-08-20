package challenge.leandrini.domains.revendas.listdealerships;

import challenge.leandrini.common.HttpStatusConstants;
import challenge.leandrini.domains.revendas.models.request.ListDealershipsRequest;
import challenge.leandrini.domains.revendas.models.response.ListDealershipsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.data.domain.Pageable;

@Tag(name = "Dealerships")
@FunctionalInterface
public interface ListDealershipsApiMethod {

    @Operation(summary = "List Dealerships")
    @ApiResponses(value = {@ApiResponse(responseCode = HttpStatusConstants.CREATED_201, description = HttpStatusConstants.CREATED_201_MESSAGE),
            @ApiResponse(responseCode = HttpStatusConstants.BAD_REQUEST_400, description = HttpStatusConstants.BAD_REQUEST_400_MESSAGE),
            @ApiResponse(responseCode = HttpStatusConstants.UNAUTHORIZED_401, description = HttpStatusConstants.UNAUTHORIZED_401_MESSAGE),
            @ApiResponse(responseCode = HttpStatusConstants.FORBIDDEN_403, description = HttpStatusConstants.FORBIDDEN_403_MESSAGE),
            @ApiResponse(responseCode = HttpStatusConstants.NOT_FOUND_404, description = HttpStatusConstants.NOT_FOUND_404_MESSAGE),
            @ApiResponse(responseCode = HttpStatusConstants.INTERNAL_SERVER_ERROR_500, description = HttpStatusConstants.INTERNAL_SERVER_ERROR_500_MESSAGE)
    })

    ListDealershipsResponse execute(Pageable pageable, @Parameter(hidden = true) ListDealershipsRequest request);

}
