package challenge.leandrini.domains.users.usecases.updateuser;

import challenge.leandrini.domains.users.models.User;

@FunctionalInterface
public interface IUpdateUserUseCase {

    User execute(UpdateUserParameters parameters);

}
