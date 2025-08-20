package challenge.leandrini.domains.users.usecases.listusers;

import challenge.leandrini.domains.users.common.ICurrentUserGateway;
import challenge.leandrini.domains.users.models.User;
import challenge.leandrini.domains.users.services.UserAuthorizationService;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
public class ListUsersUseCase implements IListUsersUseCase{

    private final IListUsersGateway listUsersGateway;
    private final ICurrentUserGateway currentUserGateway;
    private final UserAuthorizationService userAuthorizationService;

    @Override
    public ListUsersResult execute(ListUsersQuery query) {
        User currentUser = currentUserGateway.currentUser();

        String effectiveStoreId = userAuthorizationService
                .resolveEffectiveStoreForListing(currentUser, query.storeId());

        ListUsersQuery criteria = new ListUsersQuery(
                query.page(),
                query.size(),
                query.sortBy(),
                query.sortDir(),
                query.email(),
                effectiveStoreId,
                query.role(),
                query.q()
        );

        ListUsersPage page = listUsersGateway.execute(criteria);

        long total = page.totalElements();
        int size = Math.max(1, query.size());
        int totalPages = (int) Math.ceil((double) total / size);
        totalPages = Math.max(totalPages, 1);

        int currentPage = query.page();
        boolean hasPrevious = currentPage > 0;
        boolean hasNext = (currentPage + 1) < totalPages;

        return new ListUsersResult(
                page.items(),
                currentPage,
                size,
                total,
                totalPages,
                hasNext,
                hasPrevious
        );
    }
}
