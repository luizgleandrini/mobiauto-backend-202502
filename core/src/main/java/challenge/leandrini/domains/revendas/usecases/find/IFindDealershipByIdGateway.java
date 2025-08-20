package challenge.leandrini.domains.revendas.usecases.find;

import challenge.leandrini.domains.revendas.models.Dealership;

import java.util.Optional;

@FunctionalInterface
public interface IFindDealershipByIdGateway {
    Optional<Dealership> execute(String id);
}
