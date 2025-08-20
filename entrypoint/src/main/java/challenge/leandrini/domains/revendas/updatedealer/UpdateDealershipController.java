package challenge.leandrini.domains.revendas.updatedealer;

import challenge.leandrini.domains.revendas.models.request.UpdateDealershipRequest;
import challenge.leandrini.domains.revendas.models.response.UpdateDealershipResponse;
import challenge.leandrini.domains.revendas.usecases.update.IUpdateDealershipUseCase;
import challenge.leandrini.domains.revendas.usecases.update.UpdateDealershipParameters;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/dealerships")
public class UpdateDealershipController implements UpdateDealershipApiMethod {

    private final IUpdateDealershipUseCase updateDealershipUseCase;

    @Override
    @PutMapping(path = "/{dealershipId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public UpdateDealershipResponse execute(@PathVariable String dealershipId,
                                         @RequestBody @Valid @Parameter(name = "request") UpdateDealershipRequest request) {
        var parameters = new UpdateDealershipParameters(
                dealershipId,
                request.getCnpj(),
                request.getSocialName()
        );

        var dealership = updateDealershipUseCase.execute(parameters);

        return new UpdateDealershipResponse(
                dealership.getId(),
                dealership.getCnpj(),
                dealership.getSocialName());
    }
}
