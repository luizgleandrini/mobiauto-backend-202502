package challenge.leandrini.domains.oportunidade.repositories;

import challenge.leandrini.domains.oportunidade.models.OpportunityEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OpportunityRepository extends MongoRepository<OpportunityEntity, String> {
    
    Optional<OpportunityEntity> findById(String id);
    
}