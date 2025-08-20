package challenge.leandrini.domains.oportunidade.providers;

import challenge.leandrini.domains.oportunidade.mapper.IOpportunityMapper;
import challenge.leandrini.domains.oportunidade.mapper.OpportunityMapper;
import challenge.leandrini.domains.oportunidade.repositories.OpportunityRepository;
import challenge.leandrini.domains.oportunidades.models.Opportunity;
import challenge.leandrini.domains.oportunidades.usecases.create.ICreateOpportunityGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class CreateOpportunityProvider implements ICreateOpportunityGateway {

    private final IOpportunityMapper opportunityMapper = new OpportunityMapper();

    @Autowired
    private OpportunityRepository opportunityRepository;

    @Override
    public void execute(Opportunity opportunity) {
        var opportunityEntity = opportunityMapper.of(opportunity);
        opportunityRepository.save(opportunityEntity);
    }
}