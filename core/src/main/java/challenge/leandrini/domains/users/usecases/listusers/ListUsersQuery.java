package challenge.leandrini.domains.users.usecases.listusers;

public record ListUsersQuery(
        int page,
        int size,
        String sortBy,
        String sortDir,
        String email,
        String storeId,
        String role,
        String q
) {}