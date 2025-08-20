package challenge.leandrini.domains.users.common;

import challenge.leandrini.domains.users.models.User;

@FunctionalInterface
public interface ICurrentUserGateway {
    User currentUser();
}
