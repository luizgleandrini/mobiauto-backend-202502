package challenge.leandrini.domains.user.repositories;

import challenge.leandrini.domains.user.models.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, String>{

    Optional<UserEntity> findByEmail(String email);
}
