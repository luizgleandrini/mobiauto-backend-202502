package challenge.leandrini.domains.user.models;

import jakarta.validation.constraints.Size;
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
@Document(collection = "users")
public class UserEntity implements Serializable {

    @Id
    private String id;

    @Size(min = 3, max = 40)
    private String name;

    @Indexed(unique = true)
    @Size(max = 254)
    private String email;

    private String phone;

    private String password;

    private String role;

    private String storeId;

    @Indexed(direction = IndexDirection.DESCENDING)
    private Date createdAt;

    @Indexed(direction = IndexDirection.DESCENDING)
    private Date updatedAt;

}
