package challenge.leandrini.domains.revendas.create;

import challenge.leandrini.domains.revendas.models.request.CreateDealershipRequest;
import challenge.leandrini.domains.revendas.usecases.create.CreateDealershipParameters;
import challenge.leandrini.domains.revendas.usecases.create.ICreateDealershipUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/dealerships")
public class CreateDealershipController implements CreateDealershipApiMethod{

    private final ICreateDealershipUseCase createDealershipUseCase;

    @Override
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public void execute(@RequestBody @Valid CreateDealershipRequest request) {

        var parameters = new CreateDealershipParameters(
                request.getCnpj(),
                request.getSocialName()
        );

        createDealershipUseCase.execute(parameters);
    }
}
