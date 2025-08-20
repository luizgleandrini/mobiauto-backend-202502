package challenge.leandrini.domains.users.usecases.updateuser;

import challenge.leandrini.domains.users.models.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
public class UpdateUserParameters {

    @NotBlank(message = "userId is required")
    String userId;

    @Size(min = 3, max = 40, message = "Name must be between 3 and 40 characters")
    String name;

    @Email(message = "Email must be valid")
    String email;

    String phone;

    UserRole role;

    String storeId;

    public UpdateUserParameters(String userId, String name, String email, String phone, UserRole role, String storeId) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.storeId = storeId;
    }
}
