package challenge.leandrini.domains.user.providers;

import challenge.leandrini.domains.user.models.UserEntity;
import challenge.leandrini.domains.user.repositories.UserRepository;
import challenge.leandrini.domains.users.models.User;
import challenge.leandrini.domains.users.models.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("GetUserByIdProvider Test")
@ExtendWith(SpringExtension.class)
@DataMongoTest
@Import({GetUserByIdProvider.class})
@Testcontainers
public class GetUserByIdProviderTest {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0");

    @DynamicPropertySource
    static void mongoProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Autowired
    private GetUserByIdProvider getUserByIdProvider;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void shouldFindUserById() {
        UserEntity entity = new UserEntity(
                null,
                "Test User",
                "test@email.com",
                "1234567890",
                "password123",
                "ADMIN",
                "storeId1",
                new Date(),
                new Date()
        );
        UserEntity savedEntity = userRepository.save(entity);

        Optional<User> found = getUserByIdProvider.execute(savedEntity.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Test User");
        assertThat(found.get().getEmail()).isEqualTo("test@email.com");
    }

    @Test
    void shouldReturnEmptyIfUserNotFound() {
        Optional<User> found = getUserByIdProvider.execute("nonexistent-id");
        assertThat(found).isEmpty();
    }
}

