package challenge.leandrini.domains.users.models.response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserResponse {

    private String id;

    private String name;

    private String email;

    private String phone;

    private String role;

    private String storeId;

}
