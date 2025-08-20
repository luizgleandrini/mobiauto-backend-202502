package challenge.leandrini.domains.users.listusers;

import challenge.leandrini.domains.users.models.request.ListUsersRequest;
import challenge.leandrini.domains.users.usecases.listusers.ListUsersQuery;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ListUsersQueryFactory {

    private static final Set<String> ALLOWED_SORTS = Set.of(
            "createdAt", "updatedAt", "name", "email", "role", "storeId"
    );

    public ListUsersQuery from(@ParameterObject Pageable pageable,
                              @ParameterObject ListUsersRequest request) {

        int page = Math.max(0, pageable.getPageNumber());
        int size = Math.max(1, pageable.getPageSize());

        String sortBy  = "createdAt";
        String sortDir = "DESC";

        if (pageable.getSort().isSorted()) {
            Sort.Order order = pageable.getSort().iterator().next();
            String candidate = order.getProperty();
            if (!ALLOWED_SORTS.contains(candidate)) {
                candidate = "createdAt";
            }
            sortBy  = candidate;
            sortDir = order.getDirection().isAscending() ? "ASC" : "DESC";
        }

        return new ListUsersQuery(
                page,
                size,
                sortBy,
                sortDir,
                request.getEmail(),
                request.getStoreId(),
                request.getRole(),
                request.getQ()
        );
    }
}
