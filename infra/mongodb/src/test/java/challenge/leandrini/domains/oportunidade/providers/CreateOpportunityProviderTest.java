package challenge.leandrini.domains.oportunidade.providers;

import challenge.leandrini.domains.oportunidade.mapper.OpportunityMapper;
import challenge.leandrini.domains.oportunidade.repositories.OpportunityRepository;
import challenge.leandrini.domains.oportunidades.models.Opportunity;
import challenge.leandrini.domains.oportunidades.models.OpportunityStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Create an opportunity")
@DataMongoTest
@Import({CreateOpportunityProvider.class, OpportunityMapper.class})
@Testcontainers
public class CreateOpportunityProviderTest {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0");

    @DynamicPropertySource
    static void mongoProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Autowired
    private CreateOpportunityProvider createOpportunityProvider;

    @Autowired
    private OpportunityRepository opportunityRepository;

    @BeforeEach
    void initialize() {
        opportunityRepository.deleteAll();
        assertThat(opportunityRepository.count()).isZero();
    }

    @DisplayName("Successful to create an Opportunity")
    @Test
    void successToCreateAnOpportunity() {
        Opportunity newOpportunity = new Opportunity(
                null,
                "OPP-001",
                "dealershipId",
                OpportunityStatus.NOVO,
                "conclusionReason",
                "assignedUserId",
                "ClientName",
                "client@email.com",
                "11999999999",
                "Toyota",
                "Corolla",
                "XEI",
                2025,
                new Date(),
                new Date(),
                new Date(),
                new Date()
        );


        createOpportunityProvider.execute(newOpportunity);

        assertThat(opportunityRepository.count()).isEqualTo(1);
        var savedEntity = opportunityRepository.findAll().get(0);
        assertThat(savedEntity.getClientName()).isEqualTo("ClientName");
        assertThat(savedEntity.getClientEmail()).isEqualTo("client@email.com");
        assertThat(savedEntity.getClientPhone()).isEqualTo("11999999999");
        assertThat(savedEntity.getVehicleBrand()).isEqualTo("Toyota");
        assertThat(savedEntity.getVehicleModel()).isEqualTo("Corolla");
        assertThat(savedEntity.getVehicleVersion()).isEqualTo("XEI");
        assertThat(savedEntity.getVehicleYear()).isEqualTo(2025);
        assertThat(savedEntity.getCreatedAt()).isNotNull();
        assertThat(savedEntity.getUpdatedAt()).isNotNull();
    }
}
