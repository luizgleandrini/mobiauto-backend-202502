package challenge.leandrini.domains.revendas.usecases.listdealerships;

@FunctionalInterface
public interface IListDealershipsUseCase {

    ListDealershipsResult execute(ListDealershipsQuery query);

}
