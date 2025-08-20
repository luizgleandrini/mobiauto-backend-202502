package challenge.leandrini.domains.revendas.usecases.create;

import challenge.leandrini.domains.revendas.models.Dealership;

import java.util.Optional;

@FunctionalInterface
public interface IFindDealershipByCnpjGateway {

    Optional<Dealership> execute(String cnpj);

}
