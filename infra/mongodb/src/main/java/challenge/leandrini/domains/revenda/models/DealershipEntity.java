package challenge.leandrini.domains.revenda.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection = "dealerships")
public class DealershipEntity implements Serializable {

    @Id
    private String id;

    private String cnpj;

    private String socialName;

    @Indexed(direction = IndexDirection.DESCENDING)
    private Date createdAt;

    @Indexed(direction = IndexDirection.DESCENDING)
    private Date updatedAt;

}
