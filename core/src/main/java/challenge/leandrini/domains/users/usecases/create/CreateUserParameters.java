package challenge.leandrini.domains.users.usecases.create;

import challenge.leandrini.domains.users.models.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = false)
@ToString
public class CreateUserParameters {

    @NotBlank(message = "Name must no be blank")
    private String name;

    @NotBlank(message = "Email must no be blank")
    private String email;

    @NotBlank(message = "Phone must no be blank")
    private String phone;

    @NotBlank(message = "Password must no be blank")
    private String password;

    @NotNull(message = "Role must not be null")
    private UserRole role;

    private String storeId;

    public CreateUserParameters(String name, String email, String phone, String password) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.role = UserRole.ASSISTANT;
        this.storeId = null;
    }

    public CreateUserParameters(String name, String email, String phone, String password, UserRole role, String storeId) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.role = role;
        this.storeId = storeId;
    }
}
