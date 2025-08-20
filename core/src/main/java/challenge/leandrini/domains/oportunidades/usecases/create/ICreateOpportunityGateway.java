package challenge.leandrini.domains.oportunidades.usecases.create;

import challenge.leandrini.domains.oportunidades.models.Opportunity;

@FunctionalInterface
public interface ICreateOpportunityGateway {
    void execute(Opportunity opportunity);
}