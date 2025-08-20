package challenge.leandrini.domains.revenda.repositories;

import challenge.leandrini.domains.revenda.models.DealershipEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DealershipRepository extends MongoRepository<DealershipEntity, String> {

    Optional<DealershipEntity> findByCnpj(String cnpj);

}
