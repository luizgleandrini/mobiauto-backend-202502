package challenge.leandrini.domains.users.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserItem {

    private String id;
    private String name;
    private String email;
    private String phone;
    private String role;
    private String storeId;
    private Date createdAt;
    private Date updatedAt;

}
