package challenge.leandrini.domains.revendas.usecases.create;

import challenge.leandrini.domains.revendas.models.Dealership;
import challenge.leandrini.domains.users.common.ICurrentUserGateway;
import challenge.leandrini.domains.users.models.User;
import challenge.leandrini.domains.users.models.UserRole;
import challenge.leandrini.exceptions.UniqueConstraintException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.access.AccessDeniedException;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@DisplayName("Create dealership")
class CreateDealershipUseCaseTest {

    @Mock
    private ICreateDealershipGateway createDealershipGateway = mock(ICreateDealershipGateway.class);
    @Mock
    private IFindDealershipByCnpjGateway findDealershipByCnpjGateway = mock(IFindDealershipByCnpjGateway.class);
    @Mock
    private ICurrentUserGateway currentUserGateway = mock(ICurrentUserGateway.class);

    @InjectMocks
    private CreateDealershipUseCase createDealershipUseCase = new CreateDealershipUseCase(
            createDealershipGateway,
            findDealershipByCnpjGateway,
            currentUserGateway
    );

    @DisplayName("Create dealership with invalid name")
    @Test
    void createDealershipWithInvalidName() {
        CreateDealershipParameters parameters = new CreateDealershipParameters(
                "12345678000195",
                "Existing-Dealership"
        );

        final Exception exception = Assertions.assertThrows(Exception.class, () -> createDealershipUseCase.execute(parameters));

        assertThat(exception).isInstanceOf(IllegalArgumentException.class);
        final IllegalArgumentException illegalArgumentException = (IllegalArgumentException) exception;
        assertThat(exception.getMessage()).isEqualTo("Name must contain only letters, numbers and spaces");
    }

    @DisplayName("Should throw UniqueConstraintException when dealership with CNPJ already exists")
    @Test
    void shouldThrowUniqueConstraintExceptionWhenDealershipWithCnpjExists() {
        CreateDealershipParameters parameters = new CreateDealershipParameters(
                "12345678000195",
                "Existing Dealership"
        );

        Dealership existingDealership = new Dealership(
                "1",
                "12345678000195",
                "Existing Dealership",
                new Date(),
                new Date()
        );

        User createdUser = new User(
                "2",
                "Valid Name",
                "email@email.com",
                "1234567890",
                "encodedPassword",
                UserRole.ADMIN,
                "storeId123",
                new Date(),
                new Date()
        );

        when(currentUserGateway.currentUser()).thenReturn(createdUser);

        when(findDealershipByCnpjGateway.execute("12345678000195")).thenReturn(Optional.of(existingDealership));

        UniqueConstraintException exception = Assertions.assertThrows(UniqueConstraintException.class,
            () -> createDealershipUseCase.execute(parameters));

        assertThat(exception).isInstanceOf(UniqueConstraintException.class);
        assertThat(exception.getMessage()).isEqualTo("Dealership already exists for CNPJ: 12345678000195");

        verify(findDealershipByCnpjGateway).execute("12345678000195");
        verifyNoInteractions(createDealershipGateway);
    }

    @DisplayName("Should throw AccessDeniedException when current user is not ADMIN")
    @Test
    void shouldThrowAccessDeniedExceptionWhenUserIsNotAdmin() {
        CreateDealershipParameters parameters = new CreateDealershipParameters(
                "12345678000195",
                "Valid Dealership"
        );

        when(currentUserGateway.currentUser()).thenReturn(null);
        AccessDeniedException accessDeniedException = Assertions.assertThrows(AccessDeniedException.class,
                () -> createDealershipUseCase.execute(parameters)
        );
        assertThat(accessDeniedException).isInstanceOf(AccessDeniedException.class);
        assertThat(accessDeniedException.getMessage()).isEqualTo("Only ADMIN can create dealerships");

        User nonAdminUser = new User(
                "1",
                "User",
                "user@email.com",
                "1234567890",
                "password",
                UserRole.ASSISTANT,
                "storeId123",
                new Date(),
                new Date()
        );
        when(currentUserGateway.currentUser()).thenReturn(nonAdminUser);
        AccessDeniedException exception2 = Assertions.assertThrows(AccessDeniedException.class, () ->
                createDealershipUseCase.execute(parameters)
        );
        assertThat(exception2).isInstanceOf(AccessDeniedException.class);
        assertThat(exception2.getMessage()).isEqualTo("Only ADMIN can create dealerships");
    }

    @DisplayName("Should throw IllegalArgumentException when CNPJ is invalid")
    @Test
    void shouldThrowIllegalArgumentExceptionWhenCnpjIsInvalid() {
        CreateDealershipParameters parameters = new CreateDealershipParameters(
                "11111111111111",
                "Valid Dealership"
        );

        User adminUser = new User(
                "1",
                "Admin",
                "admin@email.com",
                "1234567890",
                "password",
                UserRole.ADMIN,
                "storeId123",
                new Date(),
                new Date()
        );
        when(currentUserGateway.currentUser()).thenReturn(adminUser);

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () ->
                createDealershipUseCase.execute(parameters)
        );
        assertThat(exception).isInstanceOf(IllegalArgumentException.class);
        assertThat(exception.getMessage()).isEqualTo("Invalid CNPJ");
    }

    @DisplayName("Should create dealership successfully when CNPJ does not exist")
    @Test
    void shouldCreateDealershipSuccessfullyWhenCnpjDoesNotExist() {
        CreateDealershipParameters parameters = new CreateDealershipParameters(
                "12345678000195",
                "New Dealership"
        );

        Date currentDate = new Date();
        when(findDealershipByCnpjGateway.execute("12345678000195")).thenReturn(Optional.empty());

        Dealership createdDealership = new Dealership(
                "1",
                "12345678000195",
                "New Dealership",
                currentDate,
                currentDate
        );

        User createdUser = new User(
                "2",
                "Valid Name",
                "email@email.com",
                "1234567890",
                "encodedPassword",
                UserRole.ADMIN,
                "storeId123",
                new Date(),
                new Date()
        );

        when(currentUserGateway.currentUser()).thenReturn(createdUser);

        doNothing().when(createDealershipGateway).execute(any(Dealership.class));

        createDealershipUseCase.execute(parameters);

        verify(findDealershipByCnpjGateway).execute("12345678000195");
        verify(createDealershipGateway).execute(argThat(dealership ->
            dealership.getCnpj().equals("12345678000195") &&
            dealership.getSocialName().equals("New Dealership") &&
            dealership.getCreatedAt() != null &&
            dealership.getUpdatedAt() != null
        ));
    }

    @DisplayName("Should validate empty CNPJ")
    @Test
    void shouldValidateEmptyCnpj() {
        CreateDealershipParameters parameters = new CreateDealershipParameters(
                "",
                "Valid Dealership"
        );

        when(findDealershipByCnpjGateway.execute("")).thenReturn(Optional.empty());

        assertThat(parameters.getCnpj()).isEmpty();
    }

    @DisplayName("Should validate empty social name")
    @Test
    void shouldValidateEmptySocialName() {
        CreateDealershipParameters parameters = new CreateDealershipParameters(
                "12345678000195",
                ""
        );

        when(findDealershipByCnpjGateway.execute("12345678000195")).thenReturn(Optional.empty());

        assertThat(parameters.getSocialName()).isEmpty();
    }

}