package challenge.leandrini.domains.users.usecases.updateuser;

import challenge.leandrini.domains.users.models.User;

@FunctionalInterface
public interface IUpdateUserGateway {

    User execute(User user);

}
