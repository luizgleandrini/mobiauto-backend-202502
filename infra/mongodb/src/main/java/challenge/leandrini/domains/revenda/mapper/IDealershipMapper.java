package challenge.leandrini.domains.revenda.mapper;

import challenge.leandrini.domains.revenda.models.DealershipEntity;
import challenge.leandrini.domains.revendas.models.Dealership;

public interface IDealershipMapper {

    Dealership of(final DealershipEntity dealershipEntity);
    DealershipEntity of(final Dealership dealership);

}
