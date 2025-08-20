package challenge.leandrini.domains.revendas.listdealerships;

import challenge.leandrini.domains.revendas.models.Dealership;
import challenge.leandrini.domains.revendas.models.response.DealershipItem;
import challenge.leandrini.domains.revendas.models.response.ListDealershipsResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DealershipsPresenter {

    public ListDealershipsResponse present(challenge.leandrini.domains.revendas.usecases.listdealerships.ListDealershipsResult result) {
        List<DealershipItem> items = result.items().stream()
                .map(this::toItem)
                .toList();

        return new ListDealershipsResponse(
                items,
                result.page(),
                result.size(),
                result.totalElements(),
                result.totalPages(),
                result.hasNext(),
                result.hasPrevious()
        );
    }

    private DealershipItem toItem(Dealership dealership) {
        return new DealershipItem(
                dealership.getId(),
                dealership.getCnpj(),
                dealership.getSocialName(),
                dealership.getCreatedAt(),
                dealership.getUpdatedAt()
        );
    }
}
