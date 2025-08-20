package challenge.leandrini.domains.oportunidades.usecases.update;

import challenge.leandrini.domains.oportunidades.models.Opportunity;

@FunctionalInterface
public interface IGetOpportunityByIdGateway {
    Opportunity execute(String id);
}