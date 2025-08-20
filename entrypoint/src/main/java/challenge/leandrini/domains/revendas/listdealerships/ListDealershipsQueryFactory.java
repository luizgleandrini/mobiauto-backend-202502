package challenge.leandrini.domains.revendas.listdealerships;

import challenge.leandrini.domains.revendas.models.request.ListDealershipsRequest;
import challenge.leandrini.domains.revendas.usecases.listdealerships.ListDealershipsQuery;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ListDealershipsQueryFactory {

    private static final Set<String> ALLOWED_SORT =
            Set.of("createdAt", "updatedAt", "socialName", "cnpj");

    public ListDealershipsQuery from(@ParameterObject Pageable pageable,
                                     @ParameterObject ListDealershipsRequest request) {

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

        return new ListDealershipsQuery(
                page,
                size,
                sortBy,
                sortDir,
                request.getCnpj(),
                request.getSocialName(),
                request.getQ()
        );
    }
}
