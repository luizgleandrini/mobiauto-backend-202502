package challenge.leandrini.domains.users.usecases.listusers;

@FunctionalInterface
public interface IListUsersUseCase {

    ListUsersResult execute(ListUsersQuery query);

}
