package challenge.leandrini.domains.oportunidade.providers;

import challenge.leandrini.domains.oportunidade.repositories.OpportunityRepository;
import challenge.leandrini.domains.oportunidades.models.Opportunity;
import challenge.leandrini.domains.oportunidades.models.OpportunityStatus;
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

@DisplayName("UpdateOpportunityProvider Test")
@ExtendWith(SpringExtension.class)
@DataMongoTest
@Import({UpdateOpportunityProvider.class})
@Testcontainers
public class UpdateOpportunityProviderTest {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0");

    @DynamicPropertySource
    static void mongoProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Autowired
    private UpdateOpportunityProvider updateOpportunityProvider;

    @Autowired
    private OpportunityRepository opportunityRepository;

    @BeforeEach
    void setUp() {
        opportunityRepository.deleteAll();
    }

    @Test
    void shouldUpdateOpportunity() {
        Opportunity opportunity = new Opportunity(
                null,
                "OPP-001",
                "dealershipId1",
                OpportunityStatus.NOVO,
                null,
                null,
                "ClientA",
                "clientA@email.com",
                "1111111111",
                "Toyota",
                "Corolla",
                "XEI",
                2025,
                new Date(),
                new Date(),
                null,
                null
        );

        updateOpportunityProvider.execute(opportunity);

        var saved = opportunityRepository.findAll();
        assertThat(saved).hasSize(1);
        assertThat(saved.get(0).getCode()).isEqualTo("OPP-001");
        assertThat(saved.get(0).getClientName()).isEqualTo("ClientA");
    }
}

