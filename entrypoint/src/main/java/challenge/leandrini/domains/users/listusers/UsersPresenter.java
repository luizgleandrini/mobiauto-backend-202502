package challenge.leandrini.domains.users.listusers;

import challenge.leandrini.domains.users.models.User;
import challenge.leandrini.domains.users.models.response.ListUsersResponse;
import challenge.leandrini.domains.users.models.response.UserItem;
import challenge.leandrini.domains.users.usecases.listusers.ListUsersResult;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsersPresenter {

    public ListUsersResponse present(ListUsersResult result) {
        List<UserItem> items = result.items().stream()
                .map(this::toItem)
                .toList();

        return new ListUsersResponse(
                items,
                result.page(),
                result.size(),
                result.totalElements(),
                result.totalPages(),
                result.hasNext(),
                result.hasPrevious()
        );
    }

    private UserItem toItem(User user) {
        return new UserItem(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getRole().name(),
                user.getStoreId(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

}
