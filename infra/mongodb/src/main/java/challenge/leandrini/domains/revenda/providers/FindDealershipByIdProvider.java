package challenge.leandrini.domains.revenda.providers;

import challenge.leandrini.domains.revenda.mapper.DealershipMapper;
import challenge.leandrini.domains.revenda.mapper.IDealershipMapper;
import challenge.leandrini.domains.revenda.models.DealershipEntity;
import challenge.leandrini.domains.revenda.repositories.DealershipRepository;
import challenge.leandrini.domains.revendas.models.Dealership;
import challenge.leandrini.domains.revendas.usecases.find.IFindDealershipByIdGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FindDealershipByIdProvider implements IFindDealershipByIdGateway {

    private final IDealershipMapper dealershipMapper = new DealershipMapper();

    @Autowired
    private DealershipRepository dealershipRepository;

    @Override
    public Optional<Dealership> execute(String id) {
        Optional<DealershipEntity> entityOptional = dealershipRepository.findById(id);
        return entityOptional.map(dealershipMapper::of);
    }
}
