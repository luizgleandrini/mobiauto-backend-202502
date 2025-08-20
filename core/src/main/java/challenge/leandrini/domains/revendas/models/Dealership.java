package challenge.leandrini.domains.revendas.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dealership {

    private String id;
    private String cnpj;
    private String socialName;
    private Date createdAt;
    private Date updatedAt;

}
