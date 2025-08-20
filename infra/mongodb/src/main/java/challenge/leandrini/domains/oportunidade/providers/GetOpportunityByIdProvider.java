package challenge.leandrini.domains.oportunidade.providers;

import challenge.leandrini.domains.oportunidade.mapper.IOpportunityMapper;
import challenge.leandrini.domains.oportunidade.mapper.OpportunityMapper;
import challenge.leandrini.domains.oportunidade.repositories.OpportunityRepository;
import challenge.leandrini.domains.oportunidades.models.Opportunity;
import challenge.leandrini.domains.oportunidades.usecases.update.IGetOpportunityByIdGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetOpportunityByIdProvider implements IGetOpportunityByIdGateway {

    private final IOpportunityMapper opportunityMapper = new OpportunityMapper();

    @Autowired
    private OpportunityRepository opportunityRepository;

    @Override
    public Opportunity execute(String id) {
        var opportunityEntity = opportunityRepository.findById(id);
        return opportunityEntity.map(opportunityMapper::of).orElse(null);
    }
}