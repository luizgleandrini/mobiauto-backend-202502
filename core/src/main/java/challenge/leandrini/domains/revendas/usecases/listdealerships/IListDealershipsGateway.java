package challenge.leandrini.domains.revendas.usecases.listdealerships;

@FunctionalInterface
public interface IListDealershipsGateway {
    ListDealershipsPage execute(ListDealershipsQuery query);
}
