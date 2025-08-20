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

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("UpdateUserProvider Test")
@ExtendWith(SpringExtension.class)
@DataMongoTest
@Import({UpdateUserProvider.class})
@Testcontainers
public class UpdateUserProviderTest {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0");

    @DynamicPropertySource
    static void mongoProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Autowired
    private UpdateUserProvider updateUserProvider;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void shouldUpdateUser() {
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

        User updatedUser = new User(
                savedEntity.getId(),
                "Updated User",
                "updated@email.com",
                "0987654321",
                "newpassword",
                UserRole.ASSISTANT,
                "storeId2",
                savedEntity.getCreatedAt(),
                new Date()
        );
        User result = updateUserProvider.execute(updatedUser);

        assertThat(result.getId()).isEqualTo(savedEntity.getId());
        assertThat(result.getName()).isEqualTo("Updated User");
        assertThat(result.getEmail()).isEqualTo("updated@email.com");
        assertThat(result.getPhone()).isEqualTo("0987654321");
        assertThat(result.getPassword()).isEqualTo("newpassword");
        assertThat(result.getRole()).isEqualTo(UserRole.ASSISTANT);
        assertThat(result.getStoreId()).isEqualTo("storeId2");
    }
}

