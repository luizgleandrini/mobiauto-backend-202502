package challenge.leandrini.domains.oportunidades.list;

import challenge.leandrini.domains.oportunidades.models.request.ListOpportunitiesRequest;
import challenge.leandrini.domains.oportunidades.usecases.list.ListOpportunitiesQuery;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ListOpportunitiesQueryFactory {

    private static final Set<String> ALLOWED_SORT =
            Set.of("createdAt", "updatedAt", "code", "clientName", "status", "dealershipId");

    public ListOpportunitiesQuery from(@ParameterObject Pageable pageable,
                                     @ParameterObject ListOpportunitiesRequest request) {

        int page = Math.max(0, pageable.getPageNumber());
        int size = Math.max(1, pageable.getPageSize());

        String sortBy  = "createdAt";
        String sortDir = "DESC";

        if (pageable.getSort().isSorted()) {
            Sort.Order order = pageable.getSort().iterator().next();
            String candidate = order.getProperty();
            if (!ALLOWED_SORT.contains(candidate)) {
                candidate = "createdAt";
            }
            sortBy  = candidate;
            sortDir = order.getDirection().isAscending() ? "ASC" : "DESC";
        }

        return new ListOpportunitiesQuery(
                page,
                size,
                sortBy,
                sortDir,
                request.getDealershipId(),
                request.getStatus(),
                request.getClientName(),
                request.getClientEmail(),
                request.getVehicleBrand(),
                request.getQ()
        );
    }
}