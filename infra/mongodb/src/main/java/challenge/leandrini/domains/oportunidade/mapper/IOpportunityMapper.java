package challenge.leandrini.domains.oportunidade.mapper;

import challenge.leandrini.domains.oportunidade.models.OpportunityEntity;
import challenge.leandrini.domains.oportunidades.models.Opportunity;

public interface IOpportunityMapper {

    Opportunity of(final OpportunityEntity opportunityEntity);
    OpportunityEntity of(final Opportunity opportunity);
}