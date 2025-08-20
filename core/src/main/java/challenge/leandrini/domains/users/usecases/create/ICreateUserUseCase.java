package challenge.leandrini.domains.users.usecases.create;

@FunctionalInterface
public interface ICreateUserUseCase {

    void execute(final CreateUserParameters parameters);

}
