package challenge.leandrini.domains.users.create;

import challenge.leandrini.domains.users.models.request.CreateUserRequest;
import challenge.leandrini.domains.users.usecases.create.CreateUserParameters;
import challenge.leandrini.domains.users.usecases.create.ICreateUserUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class CreateUserController implements CreateUserApiMethod {

    private final ICreateUserUseCase createUserUseCase;

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("@userAuth.canCreateUser(authentication, #request.role, #request.storeId)")
    public void execute(@RequestBody @Valid @P("request") CreateUserRequest request) {
        CreateUserParameters parameters = new CreateUserParameters(
                request.getName(),
                request.getEmail(),
                request.getPhone(),
                request.getPassword(),
                request.getRole(),
                request.getStoreId()
        );
        createUserUseCase.execute(parameters);
    }
}
