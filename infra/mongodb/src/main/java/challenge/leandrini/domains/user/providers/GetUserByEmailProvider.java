package challenge.leandrini.domains.user.providers;

import challenge.leandrini.domains.user.mapper.IUserMapper;
import challenge.leandrini.domains.user.mapper.UserMapper;
import challenge.leandrini.domains.user.models.UserEntity;
import challenge.leandrini.domains.user.repositories.UserRepository;
import challenge.leandrini.domains.users.models.User;
import challenge.leandrini.domains.users.usecases.create.IGetUserByEmailGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetUserByEmailProvider implements IGetUserByEmailGateway {

    private final IUserMapper userMapper = new UserMapper();

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> execute(String email) {
        final Optional<UserEntity> optional = userRepository.findByEmail(email);
        return optional.map(userMapper::of);
    }
}
