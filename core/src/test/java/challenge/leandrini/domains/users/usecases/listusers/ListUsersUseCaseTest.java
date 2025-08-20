package challenge.leandrini.domains.users.usecases.listusers;

import challenge.leandrini.domains.users.common.ICurrentUserGateway;
import challenge.leandrini.domains.users.models.User;
import challenge.leandrini.domains.users.models.UserRole;
import challenge.leandrini.domains.users.services.UserAuthorizationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@DisplayName("List users")
class ListUsersUseCaseTest {

    @Mock
    private IListUsersGateway listUsersGateway = mock(IListUsersGateway.class);
    @Mock
    private ICurrentUserGateway currentUserGateway = mock(ICurrentUserGateway.class);
    @Mock
    private UserAuthorizationService userAuthorizationService = mock(UserAuthorizationService.class);

    @InjectMocks
    private ListUsersUseCase listUsersUseCase = new ListUsersUseCase(
            listUsersGateway,
            currentUserGateway,
            userAuthorizationService
    );

    @Test
    @DisplayName("Should list users with correct pagination and criteria")
    void shouldListUsersWithCorrectPaginationAndCriteria() {
        User currentUser = new User("1", "Admin", "admin@email.com", "1234567890", "password", UserRole.ADMIN, "storeId123", null, null);
        when(currentUserGateway.currentUser()).thenReturn(currentUser);
        when(userAuthorizationService.resolveEffectiveStoreForListing(currentUser, "storeId123")).thenReturn("storeId123");
        ListUsersQuery query = new ListUsersQuery(0, 10, "name", "asc", null, "storeId123", null, null);
        List<User> users = List.of(currentUser);
        ListUsersPage page = new ListUsersPage(users, 1);
        when(listUsersGateway.execute(any(ListUsersQuery.class))).thenReturn(page);

        ListUsersResult result = listUsersUseCase.execute(query);

        assertThat(result.items()).hasSize(1);
        assertThat(result.page()).isEqualTo(0);
        assertThat(result.size()).isEqualTo(10);
        assertThat(result.totalElements()).isEqualTo(1);
        assertThat(result.totalPages()).isEqualTo(1);
        assertThat(result.hasPrevious()).isFalse();
        assertThat(result.hasNext()).isFalse();
    }

    @Test
    @DisplayName("Should handle empty result and pagination correctly")
    void shouldHandleEmptyResultAndPagination() {
        User currentUser = new User("1", "Admin", "admin@email.com", "1234567890", "password", UserRole.ADMIN, "storeId123", null, null);
        when(currentUserGateway.currentUser()).thenReturn(currentUser);
        when(userAuthorizationService.resolveEffectiveStoreForListing(currentUser, "storeId123")).thenReturn("storeId123");
        ListUsersQuery query = new ListUsersQuery(0, 10, "name", "asc", null, "storeId123", null, null);
        ListUsersPage page = new ListUsersPage(Collections.emptyList(), 0);
        when(listUsersGateway.execute(any(ListUsersQuery.class))).thenReturn(page);

        ListUsersResult result = listUsersUseCase.execute(query);

        assertThat(result.items()).isEmpty();
        assertThat(result.totalElements()).isEqualTo(0);
        assertThat(result.totalPages()).isEqualTo(1);
        assertThat(result.hasPrevious()).isFalse();
        assertThat(result.hasNext()).isFalse();
    }

    @Test
    @DisplayName("Should resolve effective store id using authorization service")
    void shouldResolveEffectiveStoreId() {
        User currentUser = new User("1", "Admin", "admin@email.com", "1234567890", "password", UserRole.ADMIN, "storeId123", null, null);
        when(currentUserGateway.currentUser()).thenReturn(currentUser);
        when(userAuthorizationService.resolveEffectiveStoreForListing(currentUser, "storeId123")).thenReturn("effectiveStoreId");
        ListUsersQuery query = new ListUsersQuery(0, 10, "name", "asc", null, "storeId123", null, null);
        ListUsersPage page = new ListUsersPage(Collections.emptyList(), 0);
        when(listUsersGateway.execute(any(ListUsersQuery.class))).thenReturn(page);

        listUsersUseCase.execute(query);

        verify(userAuthorizationService).resolveEffectiveStoreForListing(currentUser, "storeId123");
    }

    @Test
    @DisplayName("Should handle null current user gracefully")
    void shouldHandleNullCurrentUser() {
        when(currentUserGateway.currentUser()).thenReturn(null);
        when(userAuthorizationService.resolveEffectiveStoreForListing(null, "storeId123")).thenReturn("storeId123");
        ListUsersQuery query = new ListUsersQuery(0, 10, "name", "asc", null, "storeId123", null, null);
        ListUsersPage page = new ListUsersPage(Collections.emptyList(), 0);
        when(listUsersGateway.execute(any(ListUsersQuery.class))).thenReturn(page);

        ListUsersResult result = listUsersUseCase.execute(query);

        assertThat(result.items()).isEmpty();
        assertThat(result.totalElements()).isEqualTo(0);
    }
}