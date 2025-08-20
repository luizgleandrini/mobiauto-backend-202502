package challenge.leandrini.domains.users.usecases.updateuser;

import challenge.leandrini.domains.users.models.User;
import challenge.leandrini.domains.users.models.UserRole;
import challenge.leandrini.domains.users.services.UserAuthorizationService;
import challenge.leandrini.domains.users.usecases.common.IGetUserByIdGateway;
import challenge.leandrini.exceptions.NotFoundException;
import challenge.leandrini.IGetDate;
import challenge.leandrini.domains.users.common.ICurrentUserGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@DisplayName("Update user")
class UpdateUserUseCaseTest {
    private IGetUserByIdGateway getUserByIdGateway;
    private IUpdateUserGateway updateUserGateway;
    private ICurrentUserGateway currentUserGateway;
    private UserAuthorizationService userAuthorizationService;
    private IGetDate getDate;
    private UpdateUserUseCase useCase;

    @BeforeEach
    void setUp() {
        getUserByIdGateway = mock(IGetUserByIdGateway.class);
        updateUserGateway = mock(IUpdateUserGateway.class);
        currentUserGateway = mock(ICurrentUserGateway.class);
        userAuthorizationService = mock(UserAuthorizationService.class);
        getDate = mock(IGetDate.class);
        useCase = new UpdateUserUseCase(getUserByIdGateway, updateUserGateway, currentUserGateway, userAuthorizationService, getDate);
    }

    @Test
    @DisplayName("Should update user successfully")
    void shouldUpdateUserSuccessfully() {
        User currentUser = new User("admin", "Admin", "admin@email.com", "1234567890", "password", UserRole.ADMIN, "storeId123", new Date(), new Date());
        User targetUser = new User("1", "Old Name", "user@email.com", "1111111111", "password", UserRole.ASSISTANT, "storeId123", new Date(), new Date());
        Date now = new Date();
        when(currentUserGateway.currentUser()).thenReturn(currentUser);
        when(getUserByIdGateway.execute("1")).thenReturn(Optional.of(targetUser));
        doNothing().when(userAuthorizationService).validateUserEditPermission(currentUser, targetUser);
        when(getDate.now()).thenReturn(now);
        when(updateUserGateway.execute(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UpdateUserParameters params = new UpdateUserParameters("1", "New Name", "email@email.com", "9999999999", UserRole.ADMIN, "newStoreId");
        User result = useCase.execute(params);

        assertThat(result.getName()).isEqualTo("New Name");
        assertThat(result.getPhone()).isEqualTo("9999999999");
        assertThat(result.getRole()).isEqualTo(UserRole.ADMIN);
        assertThat(result.getStoreId()).isEqualTo("newStoreId");
        assertThat(result.getUpdatedAt()).isEqualTo(now);
        verify(updateUserGateway).execute(targetUser);
    }

    @Test
    @DisplayName("Should throw NotFoundException when user does not exist")
    void shouldThrowNotFoundExceptionWhenUserDoesNotExist() {
        User currentUser = new User("admin", "Admin", "admin@email.com", "1234567890", "password", UserRole.ADMIN, "storeId123", new Date(), new Date());
        when(currentUserGateway.currentUser()).thenReturn(currentUser);
        when(getUserByIdGateway.execute("2")).thenReturn(Optional.empty());

        UpdateUserParameters params = new UpdateUserParameters("2", "Name", "email@email.com", "9999999999", UserRole.ADMIN, "storeId123");
        NotFoundException exception = assertThrows(NotFoundException.class, () -> useCase.execute(params));
        assertThat(exception.getMessage()).contains("User not found2");
    }

    @Test
    @DisplayName("Should only update provided fields")
    void shouldOnlyUpdateProvidedFields() {
        User currentUser = new User("admin", "Admin", "admin@email.com", "1234567890", "password", UserRole.ADMIN, "storeId123", new Date(), new Date());
        User targetUser = new User("3", "Old Name", "user@email.com", "1111111111", "password", UserRole.ASSISTANT, "storeId123", new Date(), new Date());
        Date now = new Date();
        when(currentUserGateway.currentUser()).thenReturn(currentUser);
        when(getUserByIdGateway.execute("3")).thenReturn(Optional.of(targetUser));
        doNothing().when(userAuthorizationService).validateUserEditPermission(currentUser, targetUser);
        when(getDate.now()).thenReturn(now);
        when(updateUserGateway.execute(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UpdateUserParameters params = new UpdateUserParameters("3", null, "email@email.com", "8888888888", null, null);
        User result = useCase.execute(params);
        assertThat(result.getName()).isEqualTo("Old Name");
        assertThat(result.getPhone()).isEqualTo("8888888888");
        assertThat(result.getRole()).isEqualTo(UserRole.ASSISTANT);
        assertThat(result.getStoreId()).isEqualTo("storeId123");
        assertThat(result.getUpdatedAt()).isEqualTo(now);
        verify(updateUserGateway).execute(targetUser);
    }
}

