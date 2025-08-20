package challenge.leandrini.domains.revendas.usecases.update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
public class UpdateDealershipParameters {

    @NotBlank(message = "dealershipId is required")
    String dealershipId;

    @Pattern(regexp = "^\\d{14}$", message = "CNPJ must contain exactly 14 digits")
    String cnpj;

    @Size(min = 3, max = 100, message = "Social name must be between 3 and 100 characters")
    String socialName;

    public UpdateDealershipParameters(String dealershipId, String cnpj, String socialName) {
        this.dealershipId = dealershipId;
        this.cnpj = cnpj;
        this.socialName = socialName;
    }
}
