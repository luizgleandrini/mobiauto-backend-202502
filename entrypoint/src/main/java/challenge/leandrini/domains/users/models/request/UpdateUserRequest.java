package challenge.leandrini.domains.users.models.request;

import challenge.leandrini.domains.users.models.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateUserRequest {

    @Size(min = 3, max = 40)
    private String name;

    @Email
    private String email;

    private String phone;

    private UserRole role;

    private String storeId;

}
