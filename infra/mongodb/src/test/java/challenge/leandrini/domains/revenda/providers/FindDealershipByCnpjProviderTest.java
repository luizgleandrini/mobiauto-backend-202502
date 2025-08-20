package challenge.leandrini.domains.revenda.providers;

import challenge.leandrini.domains.revenda.models.DealershipEntity;
import challenge.leandrini.domains.revenda.repositories.DealershipRepository;
import challenge.leandrini.domains.revendas.models.Dealership;
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

@DisplayName("FindDealershipByCnpjProvider Test")
@ExtendWith(SpringExtension.class)
@DataMongoTest
@Import({FindDealershipByCnpjProvider.class})
@Testcontainers
public class FindDealershipByCnpjProviderTest {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0");

    @DynamicPropertySource
    static void mongoProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Autowired
    private FindDealershipByCnpjProvider findDealershipByCnpjProvider;

    @Autowired
    private DealershipRepository dealershipRepository;

    @BeforeEach
    void setUp() {
        dealershipRepository.deleteAll();
    }

    @Test
    void shouldFindDealershipByCnpj() {
        DealershipEntity dealershipEntity = new DealershipEntity(
                null,
                "12345678901234",
                "Test Social Name",
                new Date(),
                new Date()
        );
        dealershipRepository.save(dealershipEntity);

        Optional<Dealership> found = findDealershipByCnpjProvider.execute("12345678901234");

        assertThat(found).isPresent();
        assertThat(found.get().getCnpj()).isEqualTo("12345678901234");
        assertThat(found.get().getSocialName()).isEqualTo("Test Social Name");
    }

    @Test
    void shouldReturnEmptyIfDealershipNotFound() {
        Optional<Dealership> found = findDealershipByCnpjProvider.execute("00000000000000");
        assertThat(found).isEmpty();
    }
}
