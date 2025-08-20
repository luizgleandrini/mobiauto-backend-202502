package challenge.leandrini.domains.users.usecases.listusers;

import challenge.leandrini.domains.users.models.User;

import java.util.List;

public record ListUsersResult(
        List<User> items,
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean hasNext,
        boolean hasPrevious
) {}
