package challenge.leandrini.domains.user.providers;

import challenge.leandrini.domains.user.mapper.IUserMapper;
import challenge.leandrini.domains.user.mapper.UserMapper;
import challenge.leandrini.domains.user.models.UserEntity;
import challenge.leandrini.domains.user.repositories.UserRepository;
import challenge.leandrini.domains.users.models.User;
import challenge.leandrini.domains.users.usecases.create.ICreateUserGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateUserProvider implements ICreateUserGateway {

    private final IUserMapper userMapper = new UserMapper();

    @Autowired
    private UserRepository userRepository;

    @Override
    public void execute(User user) {
        UserEntity entity = userMapper.of(user);
        userRepository.save(entity);
    }
}
