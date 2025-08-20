package challenge.leandrini.domains.oportunidade.providers;

import challenge.leandrini.domains.oportunidade.mapper.OpportunityMapper;
import challenge.leandrini.domains.oportunidade.models.OpportunityEntity;
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

@DisplayName("Get opportunity by id")
@ExtendWith(SpringExtension.class)
@DataMongoTest
@Import({GetOpportunityByIdProvider.class, OpportunityMapper.class})
@Testcontainers
public class GetOpportunityByIdProviderTest {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0");

    @DynamicPropertySource
    static void mongoProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Autowired
    private GetOpportunityByIdProvider getOpportunityByIdProvider;

    @Autowired
    private OpportunityRepository opportunityRepository;

    @Autowired
    private OpportunityMapper opportunityMapper;

    @BeforeEach
    void initialize() {
        opportunityRepository.deleteAll();
        assertThat(opportunityRepository.count()).isZero();
    }

    @DisplayName("Should return opportunity by id")
    @Test
    void shouldReturnOpportunityById() {
        Opportunity opportunity = new Opportunity(
                null,
                "OPP-002",
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
                null,
                null
        );
        OpportunityEntity entity = opportunityMapper.of(opportunity);
        var savedEntity = opportunityRepository.save(entity);
        assertThat(savedEntity.getId()).isNotNull();

        Opportunity found = getOpportunityByIdProvider.execute(savedEntity.getId());
        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(savedEntity.getId());
        assertThat(found.getClientName()).isEqualTo("ClientName");
        assertThat(found.getClientEmail()).isEqualTo("client@email.com");
        assertThat(found.getVehicleBrand()).isEqualTo("Toyota");
    }

    @DisplayName("Should return null if opportunity not found")
    @Test
    void shouldReturnNullIfOpportunityNotFound() {
        Opportunity found = getOpportunityByIdProvider.execute("nonexistent-id");
        assertThat(found).isNull();
    }
}
