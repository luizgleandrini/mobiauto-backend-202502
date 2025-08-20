package challenge.leandrini.domains.users.usecases.create;

import challenge.leandrini.IGetDate;
import challenge.leandrini.domains.users.common.ICurrentUserGateway;
import challenge.leandrini.domains.users.models.User;
import challenge.leandrini.domains.users.services.UserAuthorizationService;
import challenge.leandrini.exceptions.UniqueConstraintException;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.regex.Pattern;

@Named
@RequiredArgsConstructor
public class CreateUserUseCase implements ICreateUserUseCase {

    private final ICreateUserGateway createUserGateway;
    private final IGetUserByEmailGateway getUserByEmailGateway;
    private final IGetDate getDate;
    private final UserAuthorizationService userAuthorizationService;
    private final ICurrentUserGateway currentUserGateway;
    private final PasswordEncoder passwordEncoder;

    private static final Pattern VALID_NAME_PATTERN = Pattern.compile("^[A-Za-zÀ-ÿ\\s]+$");

    @Override
    public void execute(CreateUserParameters parameters) {

        validateUsername(parameters.getName());

        User currentUser = currentUserGateway.currentUser();

        userAuthorizationService.validateUserCreationPermission(
                currentUser,
                parameters.getRole(),
                parameters.getStoreId()
        );

        Optional<User> optionalUser = getUserByEmailGateway.execute(parameters.getEmail());
        optionalUser.ifPresent(user -> {
            throw new UniqueConstraintException("User already exists with email: " + parameters.getEmail());
        });

        String hashedPassword = hashPassword(parameters.getPassword());

        createUserGateway.execute(new User(
                null,
                parameters.getName(),
                parameters.getEmail(),
                parameters.getPhone(),
                hashedPassword,
                parameters.getRole(),
                parameters.getStoreId(),
                getDate.now(),
                getDate.now()
        ));
    }

    private void validateUsername(String name) {
        if (!VALID_NAME_PATTERN.matcher(name).matches()) {
            throw new IllegalArgumentException("Name must contain only letters and spaces");
        }
    }

    private String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
