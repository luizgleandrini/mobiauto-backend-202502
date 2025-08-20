package challenge.leandrini.domains.oportunidades.usecases.list;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@DisplayName("List opportunities")
class ListOpportunitiesUseCaseTest {
    private IListOpportunitiesGateway listOpportunitiesGateway;
    private ListOpportunitiesUseCase listOpportunitiesUseCase;

    @BeforeEach
    void setUp() {
        listOpportunitiesGateway = mock(IListOpportunitiesGateway.class);
        listOpportunitiesUseCase = new ListOpportunitiesUseCase(listOpportunitiesGateway);
    }

    @Test
    @DisplayName("Should delegate to gateway and return result")
    void shouldDelegateToGatewayAndReturnResult() {
        ListOpportunitiesQuery query = mock(ListOpportunitiesQuery.class);
        ListOpportunitiesResult expectedResult = mock(ListOpportunitiesResult.class);
        when(listOpportunitiesGateway.execute(query)).thenReturn(expectedResult);

        ListOpportunitiesResult result = listOpportunitiesUseCase.execute(query);

        assertThat(result).isSameAs(expectedResult);
        verify(listOpportunitiesGateway).execute(query);
    }
}