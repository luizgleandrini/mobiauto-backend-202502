package challenge.leandrini.domains.users.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private String id;
    private String name;
    private String email;
    private String phone;
    private String password;
    private UserRole role;
    private String storeId;
    private Date createdAt;
    private Date updatedAt;

}
