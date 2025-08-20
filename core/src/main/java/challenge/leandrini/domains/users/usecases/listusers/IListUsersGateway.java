package challenge.leandrini.domains.users.usecases.listusers;

@FunctionalInterface
public interface IListUsersGateway {
    ListUsersPage execute(ListUsersQuery query);
}