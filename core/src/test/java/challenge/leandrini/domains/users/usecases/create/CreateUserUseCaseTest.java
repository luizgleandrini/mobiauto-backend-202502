package challenge.leandrini.domains.users.usecases.create;

import challenge.leandrini.IGetDate;
import challenge.leandrini.domains.users.common.ICurrentUserGateway;
import challenge.leandrini.domains.users.models.User;
import challenge.leandrini.domains.users.models.UserRole;
import challenge.leandrini.domains.users.services.UserAuthorizationService;
import challenge.leandrini.exceptions.UniqueConstraintException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@DisplayName("Create user")
class CreateUserUseCaseTest {

    @Mock
    private ICreateUserGateway createUserGateway = mock(ICreateUserGateway.class);
    @Mock
    private IGetUserByEmailGateway getUserByEmailGateway = mock(IGetUserByEmailGateway.class);
    @Mock
    private IGetDate getDate = mock(IGetDate.class);
    @Mock
    private UserAuthorizationService userAuthorizationService = mock(UserAuthorizationService.class);
    @Mock
    private ICurrentUserGateway currentUserGateway = mock(ICurrentUserGateway.class);
    @Mock
    private PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);

    @InjectMocks
    private CreateUserUseCase createUserUseCase = new CreateUserUseCase(
            createUserGateway,
            getUserByEmailGateway,
            getDate,
            userAuthorizationService,
            currentUserGateway,
            passwordEncoder
    );

    @DisplayName("Create user with invalid name")
    @Test
    void createUserWithInvalidName() {
        CreateUserParameters parameters = new CreateUserParameters(
                "Invalid Name 123",
                "email@email.com",
                "1234567890",
                "password123",
                UserRole.ASSISTANT,
                "storeId123"
        );

        final Exception exception = Assertions.assertThrows(Exception.class, () -> createUserUseCase.execute(parameters));

        assertThat(exception).isInstanceOf(IllegalArgumentException.class);
        final IllegalArgumentException illegalArgumentException = (IllegalArgumentException) exception;
        assertThat(exception.getMessage()).isEqualTo("Name must contain only letters and spaces");
    }

    @DisplayName("Should throw UniqueConstraintException when user with email already exists")
    @Test
    void shouldThrowUniqueConstraintExceptionWhenUserWithEmailExists() {
        CreateUserParameters parameters = new CreateUserParameters(
                "Valid Name",
                "existing@email.com",
                "1234567890",
                "password123",
                UserRole.ASSISTANT,
                "storeId123"
        );

        User currentUser = new User(
                "1",
                "Luiz",
                "email",
                "phone",
                "password",
                UserRole.ADMIN,
                "storeId123",
                new Date(),
                new Date()
        );
        when(currentUserGateway.currentUser()).thenReturn(currentUser);
        doNothing().when(userAuthorizationService).validateUserCreationPermission(
                any(User.class), eq(UserRole.ASSISTANT), eq("storeId123")
        );
        User existingUser = new User(
                "2",
                "Existing User",
                "existing@email.com",
                "1234567890",
                "encodedPassword",
                UserRole.ASSISTANT,
                "storeId123",
                new Date(),
                new Date()
        );
        when(getUserByEmailGateway.execute("existing@email.com")).thenReturn(Optional.of(existingUser));

        UniqueConstraintException exception = Assertions.assertThrows(UniqueConstraintException.class, () -> createUserUseCase.execute(parameters));

        assertThat(exception).isInstanceOf(UniqueConstraintException.class);
        UniqueConstraintException uniqueConstraintException = exception;
        assertThat(uniqueConstraintException.getMessage()).isEqualTo("User already exists with email: existing@email.com");
    }

    @DisplayName("Create user with valid name")
    @Test
    void createUserWithValidName() {
        CreateUserParameters parameters = new CreateUserParameters(
                "Valid Name",
                "email@email.com",
                "1234567890",
                "password123",
                UserRole.ASSISTANT,
                "storeId123"
        );

        User currentUser = new User(
                "1",
                "Luiz",
                "email",
                "phone",
                "password",
                UserRole.ADMIN,
                "storeId123",
                new Date(),
                new Date()
        );
        when(currentUserGateway.currentUser()).thenReturn(currentUser);
        doNothing().when(userAuthorizationService).validateUserCreationPermission(
                any(User.class), eq(UserRole.ASSISTANT), eq("storeId123")
        );
        when(getUserByEmailGateway.execute("email@email.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(getDate.now()).thenReturn(new Date());

        doNothing().when(createUserGateway).execute(any(User.class));

        createUserUseCase.execute(parameters);

        verify(createUserGateway).execute(argThat(user ->
            user.getName().equals("Valid Name") &&
            user.getEmail().equals("email@email.com") &&
            user.getRole() == UserRole.ASSISTANT &&
            user.getStoreId().equals("storeId123")
        ));
    }

}