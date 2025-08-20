package challenge.leandrini.domains.revenda.providers;

import challenge.leandrini.domains.revenda.mapper.DealershipMapper;
import challenge.leandrini.domains.revenda.mapper.IDealershipMapper;
import challenge.leandrini.domains.revenda.models.DealershipEntity;
import challenge.leandrini.domains.revenda.repositories.DealershipRepository;
import challenge.leandrini.domains.revendas.models.Dealership;
import challenge.leandrini.domains.revendas.usecases.create.ICreateDealershipGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateDealershipProvider implements ICreateDealershipGateway {

    private final IDealershipMapper dealershipMapper = new DealershipMapper();

    @Autowired
    private DealershipRepository dealershipRepository;

    @Override
    public void execute(Dealership dealership) {
        DealershipEntity entity = dealershipMapper.of(dealership);
        dealershipRepository.save(entity);
    }

}
