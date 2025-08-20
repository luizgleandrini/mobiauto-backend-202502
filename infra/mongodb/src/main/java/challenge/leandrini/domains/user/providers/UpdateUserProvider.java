package challenge.leandrini.domains.user.providers;

import challenge.leandrini.domains.user.mapper.UserMapper;
import challenge.leandrini.domains.user.models.UserEntity;
import challenge.leandrini.domains.user.repositories.UserRepository;
import challenge.leandrini.domains.users.models.User;
import challenge.leandrini.domains.users.usecases.updateuser.IUpdateUserGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateUserProvider implements IUpdateUserGateway {

    private final UserMapper userMapper = new UserMapper();

    @Autowired
    private UserRepository userRepository;

    @Override
    public User execute(User user) {
        UserEntity entity = userMapper.of(user);
        final UserEntity updatedEntity = userRepository.save(entity);
        return userMapper.of(updatedEntity);
    }
}
