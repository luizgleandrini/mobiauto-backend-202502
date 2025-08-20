package challenge.leandrini.domains.user.mapper;

import challenge.leandrini.domains.user.models.UserEntity;
import challenge.leandrini.domains.users.models.User;
import challenge.leandrini.domains.users.models.UserRole;

import java.util.Date;

public class UserMapper implements IUserMapper{
    @Override
    public User of(UserEntity userEntity) {
        String id = userEntity.getId();
        String name = userEntity.getName();
        String email = userEntity.getEmail();
        String phone = userEntity.getPhone();
        String password = userEntity.getPassword();
        UserRole role;
        try {
            role = UserRole.valueOf(userEntity.getRole().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid role value: " + userEntity.getRole() + 
                ". Valid roles are: ADMIN, OWNER, MANAGER, ASSISTANT", e);
        }
        String storeId = userEntity.getStoreId();
        Date createdAt = userEntity.getCreatedAt();
        Date updatedAt = userEntity.getUpdatedAt();

        User user = new User(
                id,
                name,
                email,
                phone,
                password,
                role,
                storeId,
                createdAt,
                updatedAt
        );
        return user;
    }

    @Override
    public UserEntity of(User user) {
        String id = user.getId();
        String name = user.getName();
        String email = user.getEmail();
        String phone = user.getPhone();
        String password = user.getPassword();
        String role = user.getRole().name();
        String storeId = user.getStoreId();
        Date createdAt = user.getCreatedAt();
        Date updatedAt = user.getUpdatedAt();

        UserEntity userEntity = new UserEntity(
                id,
                name,
                email,
                phone,
                password,
                role,
                storeId,
                createdAt,
                updatedAt
        );

        return userEntity;
    }
}
