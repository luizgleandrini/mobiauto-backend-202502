package challenge.leandrini.domains.user.providers;

import challenge.leandrini.domains.user.mapper.IUserMapper;
import challenge.leandrini.domains.user.mapper.UserMapper;
import challenge.leandrini.domains.user.models.UserEntity;
import challenge.leandrini.domains.user.repositories.UserRepository;
import challenge.leandrini.domains.users.models.User;
import challenge.leandrini.domains.users.usecases.common.IGetUserByIdGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetUserByIdProvider implements IGetUserByIdGateway {

    private final IUserMapper userMapper = new UserMapper();

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> execute(String id) {
        final Optional<UserEntity> userEntity = userRepository.findById(id);
        return userEntity.map(userMapper::of);
    }
}
