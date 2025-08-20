package challenge.leandrini.domains.revendas.usecases.update;

import challenge.leandrini.domains.revendas.models.Dealership;

@FunctionalInterface
public interface IUpdateDealershipUseCase {
    Dealership execute(UpdateDealershipParameters parameters);
}
