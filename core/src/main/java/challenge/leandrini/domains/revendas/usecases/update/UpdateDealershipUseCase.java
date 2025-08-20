package challenge.leandrini.domains.revendas.usecases.update;

import challenge.leandrini.domains.revendas.models.Dealership;
import challenge.leandrini.domains.revendas.usecases.find.IFindDealershipByIdGateway;
import challenge.leandrini.exceptions.NotFoundException;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Named
@RequiredArgsConstructor
public class UpdateDealershipUseCase implements IUpdateDealershipUseCase {

    private final IFindDealershipByIdGateway findDealershipByIdGateway;
    private final IUpdateDealershipGateway updateDealershipGateway;

    @Override
    public Dealership execute(UpdateDealershipParameters parameters) {
        Dealership dealership = findDealershipByIdGateway.execute(parameters.getDealershipId())
                .orElseThrow(() -> new NotFoundException(String.format("Dealership not found with id: %s", parameters.getDealershipId())));

        dealership.setCnpj(parameters.getCnpj());
        dealership.setSocialName(parameters.getSocialName());
        dealership.setUpdatedAt(new Date());

        return updateDealershipGateway.execute(dealership);
    }
}
