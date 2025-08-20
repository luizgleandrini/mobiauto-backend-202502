package challenge.leandrini.domains.users.updateuser;

import challenge.leandrini.domains.users.models.request.UpdateUserRequest;
import challenge.leandrini.domains.users.models.response.UpdateUserResponse;
import challenge.leandrini.domains.users.usecases.updateuser.IUpdateUserUseCase;
import challenge.leandrini.domains.users.usecases.updateuser.UpdateUserParameters;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UpdateUserController implements UpdateUserApiMethod{

    private final IUpdateUserUseCase updateUserUseCase;

    @Override
    @PutMapping(path = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("@userAuth.canEditUserProfiles(authentication)")
    public UpdateUserResponse execute(@PathVariable String userId,
                                      @RequestBody @Valid @P("request") UpdateUserRequest request) {
        var parameters = new UpdateUserParameters(
                userId,
                request.getName(),
                request.getEmail(),
                request.getPhone(),
                request.getRole(),
                request.getStoreId()
        );

        var user = updateUserUseCase.execute(parameters);

        return new UpdateUserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getRole().name(),
                user.getStoreId());
    }

}
