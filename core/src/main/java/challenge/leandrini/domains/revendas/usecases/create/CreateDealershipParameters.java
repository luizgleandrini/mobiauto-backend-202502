package challenge.leandrini.domains.revendas.usecases.create;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = false)
@ToString
public class CreateDealershipParameters {

    @NotBlank(message = "CNPJ must no be blank")
    private String cnpj;

    @NotBlank(message = "Social name must no be blank")
    private String socialName;

    public CreateDealershipParameters(String cnpj, String socialName) {
        this.cnpj = cnpj;
        this.socialName = socialName;
    }
}
