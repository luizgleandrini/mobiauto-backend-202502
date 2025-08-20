package challenge.leandrini.domains.users.usecases.create;

import challenge.leandrini.domains.users.models.User;

@FunctionalInterface
public interface
ICreateUserGateway {

    void execute(User user);

}
