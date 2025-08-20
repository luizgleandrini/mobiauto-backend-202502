package challenge.leandrini.domains.users.create;

import challenge.leandrini.common.HttpStatusConstants;
import challenge.leandrini.domains.users.models.request.CreateUserRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Users")
@FunctionalInterface
public interface CreateUserApiMethod {
    @Operation(summary = "Create User")
    @ApiResponses(value = {@ApiResponse(responseCode = HttpStatusConstants.CREATED_201, description = HttpStatusConstants.CREATED_201_MESSAGE),
            @ApiResponse(responseCode = HttpStatusConstants.BAD_REQUEST_400, description = HttpStatusConstants.BAD_REQUEST_400_MESSAGE),
            @ApiResponse(responseCode = HttpStatusConstants.UNAUTHORIZED_401, description = HttpStatusConstants.UNAUTHORIZED_401_MESSAGE),
            @ApiResponse(responseCode = HttpStatusConstants.FORBIDDEN_403, description = HttpStatusConstants.FORBIDDEN_403_MESSAGE),
            @ApiResponse(responseCode = HttpStatusConstants.NOT_FOUND_404, description = HttpStatusConstants.NOT_FOUND_404_MESSAGE),
            @ApiResponse(responseCode = HttpStatusConstants.INTERNAL_SERVER_ERROR_500, description = HttpStatusConstants.INTERNAL_SERVER_ERROR_500_MESSAGE)
    })
    void execute(final CreateUserRequest request);
}
