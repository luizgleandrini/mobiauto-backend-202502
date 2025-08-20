package challenge.leandrini.domains.revendas.models.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateDealershipResponse {

    private String id;
    private String cnpj;
    private String socialName;

}
