package challenge.leandrini.domains.revendas.usecases.update;

import challenge.leandrini.domains.revendas.models.Dealership;
import challenge.leandrini.domains.revendas.usecases.find.IFindDealershipByIdGateway;
import challenge.leandrini.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@DisplayName("Update dealership")
class UpdateDealershipUseCaseTest {
    private IFindDealershipByIdGateway findDealershipByIdGateway;
    private IUpdateDealershipGateway updateDealershipGateway;
    private UpdateDealershipUseCase useCase;

    @BeforeEach
    void setUp() {
        findDealershipByIdGateway = mock(IFindDealershipByIdGateway.class);
        updateDealershipGateway = mock(IUpdateDealershipGateway.class);
        useCase = new UpdateDealershipUseCase(findDealershipByIdGateway, updateDealershipGateway);
    }

    @Test
    @DisplayName("Should update dealership successfully")
    void shouldUpdateDealershipSuccessfully() {
        Dealership dealership = new Dealership("1", "12345678000195", "Old Name", new Date(), new Date());
        UpdateDealershipParameters params = new UpdateDealershipParameters("1", "98765432000123", "New Name");
        when(findDealershipByIdGateway.execute("1")).thenReturn(Optional.of(dealership));
        when(updateDealershipGateway.execute(any(Dealership.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Dealership result = useCase.execute(params);

        assertThat(result.getId()).isEqualTo("1");
        assertThat(result.getCnpj()).isEqualTo("98765432000123");
        assertThat(result.getSocialName()).isEqualTo("New Name");
        assertThat(result.getUpdatedAt()).isNotNull();
        verify(updateDealershipGateway).execute(dealership);
    }

    @Test
    @DisplayName("Should throw NotFoundException when dealership does not exist")
    void shouldThrowNotFoundExceptionWhenDealershipDoesNotExist() {
        UpdateDealershipParameters params = new UpdateDealershipParameters("2", "98765432000123", "New Name");
        when(findDealershipByIdGateway.execute("2")).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> useCase.execute(params));
        assertThat(exception.getMessage()).contains("Dealership not found with id: 2");
    }
}

