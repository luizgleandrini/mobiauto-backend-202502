package challenge.leandrini.domains.revendas.usecases.listdealerships;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Collections;
import java.util.List;
import challenge.leandrini.domains.revendas.models.Dealership;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@DisplayName("List dealerships")
class ListDealershipsUseCaseTest {
    private IListDealershipsGateway listDealershipsGateway;
    private ListDealershipsUseCase useCase;

    @BeforeEach
    void setUp() {
        listDealershipsGateway = mock(IListDealershipsGateway.class);
        useCase = new ListDealershipsUseCase(listDealershipsGateway);
    }

    @Test
    @DisplayName("Should list dealerships with correct pagination and criteria")
    void shouldListDealershipsWithCorrectPaginationAndCriteria() {
        ListDealershipsQuery query = new ListDealershipsQuery(0, 10, "socialName", "asc", null, null, null);
        List<Dealership> dealerships = List.of(new Dealership("1", "12345678000195", "AutoMax", null, null));
        ListDealershipsPage page = new ListDealershipsPage(dealerships, 1);
        when(listDealershipsGateway.execute(any(ListDealershipsQuery.class))).thenReturn(page);

        ListDealershipsResult result = useCase.execute(query);

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
        ListDealershipsQuery query = new ListDealershipsQuery(0, 10, "socialName", "asc", null, null, null);
        ListDealershipsPage page = new ListDealershipsPage(Collections.emptyList(), 0);
        when(listDealershipsGateway.execute(any(ListDealershipsQuery.class))).thenReturn(page);

        ListDealershipsResult result = useCase.execute(query);

        assertThat(result.items()).isEmpty();
        assertThat(result.totalElements()).isEqualTo(0);
        assertThat(result.totalPages()).isEqualTo(1);
        assertThat(result.hasPrevious()).isFalse();
        assertThat(result.hasNext()).isFalse();
    }

    @Test
    @DisplayName("Should verify gateway is called with correct criteria")
    void shouldVerifyGatewayCalledWithCorrectCriteria() {
        ListDealershipsQuery query = new ListDealershipsQuery(2, 5, "socialName", "desc", "12345678000195", "AutoMax", "search");
        ListDealershipsPage page = new ListDealershipsPage(Collections.emptyList(), 0);
        when(listDealershipsGateway.execute(any(ListDealershipsQuery.class))).thenReturn(page);

        useCase.execute(query);

        verify(listDealershipsGateway).execute(argThat(criteria ->
            criteria.page() == 2 &&
            criteria.size() == 5 &&
            "socialName".equals(criteria.sortBy()) &&
            "desc".equals(criteria.sortDir()) &&
            "12345678000195".equals(criteria.cnpj()) &&
            "AutoMax".equals(criteria.socialName()) &&
            "search".equals(criteria.q())
        ));
    }
}
