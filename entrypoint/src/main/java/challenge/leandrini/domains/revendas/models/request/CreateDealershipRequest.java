package challenge.leandrini.domains.revendas.models.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateDealershipRequest {

    private String cnpj;
    private String socialName;
}
