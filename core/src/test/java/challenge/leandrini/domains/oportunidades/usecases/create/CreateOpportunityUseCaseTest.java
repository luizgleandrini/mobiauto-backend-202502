package challenge.leandrini.domains.oportunidades.usecases.create;

import challenge.leandrini.IGetDate;
import challenge.leandrini.domains.oportunidades.models.Opportunity;
import challenge.leandrini.domains.oportunidades.models.OpportunityStatus;
import challenge.leandrini.domains.users.models.User;
import challenge.leandrini.domains.users.models.UserRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@DisplayName("Create opportunity")
class CreateOpportunityUseCaseTest {

    @Mock
    private ICreateOpportunityGateway createOpportunityGateway = mock(ICreateOpportunityGateway.class);
    @Mock
    private IGetDate getDate = mock(IGetDate.class);

    @InjectMocks
    private CreateOpportunityUseCase createOpportunityUseCase = new CreateOpportunityUseCase(
            createOpportunityGateway,
            getDate
    );

    @DisplayName("Should create opportunity successfully")
    @Test
    void shouldCreateOpportunitySuccessfully() {
        CreateOpportunityParameters parameters = new CreateOpportunityParameters(
                "dealership123",
                "John Customer",
                "john@email.com",
                "11999887766",
                "Toyota",
                "Corolla",
                "XEI 2.0",
                2024
        );

        Date currentDate = new Date();
        when(getDate.now()).thenReturn(currentDate);
        doNothing().when(createOpportunityGateway).execute(any(Opportunity.class));

        createOpportunityUseCase.execute(parameters);

        verify(createOpportunityGateway).execute(argThat(opportunity ->
            opportunity.getStatus() == OpportunityStatus.NOVO &&
            opportunity.getClientName().equals("John Customer") &&
            opportunity.getClientEmail().equals("john@email.com") &&
            opportunity.getClientPhone().equals("11999887766") &&
            opportunity.getVehicleBrand().equals("Toyota") &&
            opportunity.getVehicleModel().equals("Corolla") &&
            opportunity.getVehicleVersion().equals("XEI 2.0") &&
            opportunity.getVehicleYear() == 2024 &&
            opportunity.getDealershipId().equals("dealership123") &&
            opportunity.getCreatedAt().equals(currentDate) &&
            opportunity.getUpdatedAt().equals(currentDate)
        ));
    }

    @DisplayName("Should validate customer name")
    @Test
    void shouldValidateCustomerName() {
        CreateOpportunityParameters parameters = new CreateOpportunityParameters(
                "dealership123",
                "",
                "john@email.com",
                "11999887766",
                "Toyota",
                "Corolla",
                "XEI 2.0",
                2024
        );

        assertThat(parameters.getClientName()).isEmpty();
    }

    @DisplayName("Should validate customer email")
    @Test
    void shouldValidateCustomerEmail() {
        CreateOpportunityParameters parameters = new CreateOpportunityParameters(
                "dealership123",
                "John Customer",
                "invalid-email",
                "11999887766",
                "Toyota",
                "Corolla",
                "XEI 2.0",
                2024
        );

        assertThat(parameters.getClientEmail()).isEqualTo("invalid-email");
    }

    @DisplayName("Should validate vehicle brand")
    @Test
    void shouldValidateVehicleBrand() {
        CreateOpportunityParameters parameters = new CreateOpportunityParameters(
                "dealership123",
                "John Customer",
                "john@email.com",
                "11999887766",
                "",
                "Corolla",
                "XEI 2.0",
                2024
        );

        assertThat(parameters.getVehicleBrand()).isEmpty();
    }

    @DisplayName("Should validate vehicle model")
    @Test
    void shouldValidateVehicleModel() {
        CreateOpportunityParameters parameters = new CreateOpportunityParameters(
                "dealership123",
                "John Customer",
                "john@email.com",
                "11999887766",
                "Toyota",
                "",
                "XEI 2.0",
                2024
        );

        assertThat(parameters.getVehicleModel()).isEmpty();
    }

    @DisplayName("Should validate vehicle year")
    @Test
    void shouldValidateVehicleYear() {
        CreateOpportunityParameters parameters = new CreateOpportunityParameters(
                "dealership123",
                "John Customer",
                "john@email.com",
                "11999887766",
                "Toyota",
                "Corolla",
                "XEI 2.0",
                0
        );

        assertThat(parameters.getVehicleYear()).isEqualTo(0);
    }
}