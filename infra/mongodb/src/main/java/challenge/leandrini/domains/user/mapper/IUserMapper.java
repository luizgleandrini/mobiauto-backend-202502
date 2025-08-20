package challenge.leandrini.domains.user.mapper;

import challenge.leandrini.domains.user.models.UserEntity;
import challenge.leandrini.domains.users.models.User;

public interface IUserMapper {

    User of(final UserEntity userEntity);
    UserEntity of(final User user);
}
