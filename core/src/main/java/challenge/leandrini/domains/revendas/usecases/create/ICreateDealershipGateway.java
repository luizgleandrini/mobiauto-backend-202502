package challenge.leandrini.domains.revendas.usecases.create;

import challenge.leandrini.domains.revendas.models.Dealership;

@FunctionalInterface
public interface ICreateDealershipGateway {

    void execute(Dealership dealership);

}
