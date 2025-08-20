package challenge.leandrini.domains.oportunidades.usecases.list;

@FunctionalInterface
public interface IListOpportunitiesUseCase {
    ListOpportunitiesResult execute(ListOpportunitiesQuery query);
}