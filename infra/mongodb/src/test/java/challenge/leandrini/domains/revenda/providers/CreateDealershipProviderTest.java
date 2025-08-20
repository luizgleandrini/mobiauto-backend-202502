package challenge.leandrini.domains.revenda.providers;

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

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("CreateDealershipProvider Test")
@ExtendWith(SpringExtension.class)
@DataMongoTest
@Import({CreateDealershipProvider.class})
@Testcontainers
public class CreateDealershipProviderTest {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0");

    @DynamicPropertySource
    static void mongoProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Autowired
    private CreateDealershipProvider createDealershipProvider;

    @Autowired
    private DealershipRepository dealershipRepository;

    @BeforeEach
    void setUp() {
        dealershipRepository.deleteAll();
    }

    @Test
    void shouldCreateDealership() {
        Dealership dealership = new Dealership(
                null,
                "12345678901234",
                "Test Social Name",
                new Date(),
                new Date()
        );

        createDealershipProvider.execute(dealership);

        var saved = dealershipRepository.findAll();
        assertThat(saved).hasSize(1);
        assertThat(saved.get(0).getCnpj()).isEqualTo("12345678901234");
        assertThat(saved.get(0).getSocialName()).isEqualTo("Test Social Name");
    }
}
