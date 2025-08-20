package challenge.leandrini.domains.users.usecases.common;

import challenge.leandrini.domains.users.models.User;

import java.util.Optional;

@FunctionalInterface
public interface IGetUserByIdGateway {

    Optional<User> execute(String id);

}
