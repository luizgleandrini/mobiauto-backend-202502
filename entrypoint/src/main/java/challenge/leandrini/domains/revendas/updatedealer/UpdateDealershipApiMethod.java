package challenge.leandrini.domains.revendas.updatedealer;

import challenge.leandrini.common.HttpStatusConstants;
import challenge.leandrini.domains.revendas.models.request.UpdateDealershipRequest;
import challenge.leandrini.domains.revendas.models.response.UpdateDealershipResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Dealerships")
@FunctionalInterface
public interface UpdateDealershipApiMethod {

    @Operation(summary = "Update dealership")
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusConstants.OK_200, description = HttpStatusConstants.OK_200_MESSAGE),
            @ApiResponse(responseCode = HttpStatusConstants.BAD_REQUEST_400, description = HttpStatusConstants.BAD_REQUEST_400_MESSAGE),
            @ApiResponse(responseCode = HttpStatusConstants.UNAUTHORIZED_401, description = HttpStatusConstants.UNAUTHORIZED_401_MESSAGE),
            @ApiResponse(responseCode = HttpStatusConstants.FORBIDDEN_403, description = HttpStatusConstants.FORBIDDEN_403_MESSAGE),
            @ApiResponse(responseCode = HttpStatusConstants.NOT_FOUND_404, description = HttpStatusConstants.NOT_FOUND_404_MESSAGE),
            @ApiResponse(responseCode = HttpStatusConstants.INTERNAL_SERVER_ERROR_500, description = HttpStatusConstants.INTERNAL_SERVER_ERROR_500_MESSAGE)
    })
    UpdateDealershipResponse execute(@Parameter(in = ParameterIn.PATH, required = true, description = "Dealership ID") String dealershipId,
                                    @Parameter(name = "request") UpdateDealershipRequest request);
}
