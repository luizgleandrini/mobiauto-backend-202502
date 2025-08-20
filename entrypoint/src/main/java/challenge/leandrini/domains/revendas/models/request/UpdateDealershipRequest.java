package challenge.leandrini.domains.revendas.models.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateDealershipRequest {

    @Pattern(regexp = "^\\d{14}$", message = "CNPJ must contain exactly 14 digits")
    private String cnpj;

    @Size(min = 3, max = 100, message = "Social name must be between 3 and 100 characters")
    private String socialName;

}
