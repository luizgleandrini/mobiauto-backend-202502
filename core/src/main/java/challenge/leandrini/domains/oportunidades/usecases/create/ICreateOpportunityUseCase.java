package challenge.leandrini.domains.oportunidades.usecases.create;

@FunctionalInterface
public interface ICreateOpportunityUseCase {
    void execute(CreateOpportunityParameters parameters);
}