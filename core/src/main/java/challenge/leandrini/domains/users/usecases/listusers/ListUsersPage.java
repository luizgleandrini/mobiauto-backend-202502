package challenge.leandrini.domains.users.usecases.listusers;

import challenge.leandrini.domains.users.models.User;

import java.util.List;

public record ListUsersPage(
        List<User> items,
        long totalElements
) {}
