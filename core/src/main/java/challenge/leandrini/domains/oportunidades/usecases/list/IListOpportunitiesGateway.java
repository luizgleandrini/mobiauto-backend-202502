package challenge.leandrini.domains.oportunidades.usecases.list;

@FunctionalInterface
public interface IListOpportunitiesGateway {
    ListOpportunitiesResult execute(ListOpportunitiesQuery query);
}