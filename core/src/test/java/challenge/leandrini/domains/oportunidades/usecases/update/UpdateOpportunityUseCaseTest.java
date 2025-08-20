package challenge.leandrini.domains.oportunidades.usecases.update;

import challenge.leandrini.IGetDate;
import challenge.leandrini.domains.oportunidades.models.Opportunity;
import challenge.leandrini.domains.oportunidades.models.OpportunityStatus;
import challenge.leandrini.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@DisplayName("Update opportunity")
class UpdateOpportunityUseCaseTest {
    private IGetOpportunityByIdGateway getOpportunityByIdGateway;
    private IUpdateOpportunityGateway updateOpportunityGateway;
    private IGetDate getDate;
    private UpdateOpportunityUseCase useCase;

    @BeforeEach
    void setUp() {
        getOpportunityByIdGateway = mock(IGetOpportunityByIdGateway.class);
        updateOpportunityGateway = mock(IUpdateOpportunityGateway.class);
        getDate = mock(IGetDate.class);
        useCase = new UpdateOpportunityUseCase(getOpportunityByIdGateway, updateOpportunityGateway, getDate);
    }

    @Test
    @DisplayName("Should update opportunity successfully")
    void shouldUpdateOpportunitySuccessfully() {
        Opportunity opportunity = new Opportunity();
        opportunity.setId("1");
        opportunity.setStatus(OpportunityStatus.NOVO);
        Date now = new Date();
        when(getOpportunityByIdGateway.execute("1")).thenReturn(opportunity);
        when(getDate.now()).thenReturn(now);

        UpdateOpportunityParameters params = new UpdateOpportunityParameters(
                "1",
                OpportunityStatus.CONCLUIDO,
                "Reason",
                "assignedUserId",
                "clientName",
                "clientEmail@gmail.com",
                "clientPhone",
                "vehicleBrand",
                "vehicleModel",
                "vehicleVersion",
                2025
        );

        Opportunity result = useCase.execute(params);

        assertThat(result.getClientName()).isEqualTo("clientName");
        assertThat(result.getClientEmail()).isEqualTo("clientEmail@gmail.com");
        assertThat(result.getClientPhone()).isEqualTo("clientPhone");
        assertThat(result.getVehicleBrand()).isEqualTo("vehicleBrand");
        assertThat(result.getVehicleModel()).isEqualTo("vehicleModel");
        assertThat(result.getVehicleVersion()).isEqualTo("vehicleVersion");
        assertThat(result.getVehicleYear()).isEqualTo(2025);
        assertThat(result.getStatus()).isEqualTo(OpportunityStatus.CONCLUIDO);
        assertThat(result.getAssignedUserId()).isEqualTo("assignedUserId");
        assertThat(result.getAssignedAt()).isEqualTo(now);
        assertThat(result.getUpdatedAt()).isEqualTo(now);
        assertThat(result.getConcludedAt()).isEqualTo(now);
        assertThat(result.getConclusionReason()).isEqualTo("Reason");
        verify(updateOpportunityGateway).execute(opportunity);
    }

    @Test
    @DisplayName("Should throw NotFoundException when opportunity does not exist")
    void shouldThrowNotFoundExceptionWhenOpportunityDoesNotExist() {
        when(getOpportunityByIdGateway.execute("2")).thenReturn(null);
        UpdateOpportunityParameters params = new UpdateOpportunityParameters("2", null, null, null, null, null, null, null, null, null, null);
        NotFoundException exception = assertThrows(NotFoundException.class, () -> useCase.execute(params));
        assertThat(exception.getMessage()).contains("Opportunity not found with id: 2");
    }

    @Test
    @DisplayName("Should only update provided fields")
    void shouldOnlyUpdateProvidedFields() {
        Opportunity opportunity = new Opportunity();
        opportunity.setId("1");
        opportunity.setClientName("Old Name");
        opportunity.setStatus(OpportunityStatus.NOVO);
        Date now = new Date();
        when(getOpportunityByIdGateway.execute("1")).thenReturn(opportunity);
        when(getDate.now()).thenReturn(now);

        UpdateOpportunityParameters params = new UpdateOpportunityParameters(
            "1",
            null,
            null,
            null,
            "New Name",
            null,
            null,
            null,
            null,
            null,
            null
        );
        Opportunity result = useCase.execute(params);
        assertThat(result.getClientName()).isEqualTo("New Name");
        assertThat(result.getStatus()).isEqualTo(OpportunityStatus.NOVO);
        assertThat(result.getUpdatedAt()).isEqualTo(now);
        verify(updateOpportunityGateway).execute(opportunity);
    }

    @Test
    @DisplayName("Should handle status transition from NOVO to EM_ATENDIMENTO when assigned user changes")
    void shouldHandleStatusTransitionNovoToEmAtendimento() {
        Opportunity opportunity = new Opportunity();
        opportunity.setId("4");
        opportunity.setStatus(OpportunityStatus.NOVO);
        Date now = new Date();
        when(getOpportunityByIdGateway.execute("4")).thenReturn(opportunity);
        when(getDate.now()).thenReturn(now);

        UpdateOpportunityParameters params = new UpdateOpportunityParameters(
            "4",
            null,
            null,
            "userX",
            null,
            null,
            null,
            null,
            null,
            null,
            null
        );
        Opportunity result = useCase.execute(params);
        assertThat(result.getAssignedUserId()).isEqualTo("userX");
        assertThat(result.getAssignedAt()).isEqualTo(now);
        assertThat(result.getStatus()).isEqualTo(OpportunityStatus.EM_ATENDIMENTO);
        assertThat(result.getUpdatedAt()).isEqualTo(now);
        verify(updateOpportunityGateway).execute(opportunity);
    }
}
