package challenge.leandrini.domains.users.usecases.create;

import challenge.leandrini.domains.users.models.User;

import java.util.Optional;

@FunctionalInterface
public interface IGetUserByEmailGateway {
    Optional<User> execute(final String email);

}
