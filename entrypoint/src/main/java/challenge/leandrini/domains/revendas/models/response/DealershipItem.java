package challenge.leandrini.domains.revendas.models.response;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DealershipItem {

    private String id;
    private String cnpj;
    private String socialName;
    private Date createdAt;
    private Date updatedAt;

}
