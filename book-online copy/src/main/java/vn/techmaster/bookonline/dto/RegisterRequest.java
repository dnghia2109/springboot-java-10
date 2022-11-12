package vn.techmaster.bookonline.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import vn.techmaster.bookonline.annotation.UniqueUserEmail;
import vn.techmaster.bookonline.annotation.UniqueUsername;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class RegisterRequest implements Serializable {
    @NotBlank(message = "Email must not be empty")
    @Email(message = "Email must be valid")
    @UniqueUserEmail(message = "This email is used")
    private String email;

    @NotBlank(message = "Username must not be empty")
    @UniqueUsername(message = "This username is used")
    private String username;

    @NotBlank(message = "Full name must not be empty")
    private String fullName;

    @NotBlank(message = "Password must not be empty")
    private String password;

    public RegisterRequest(String email, String password) {
        this.email = email.strip();
        this.username = username.strip();
        this.fullName = fullName.strip();
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email.strip();
    }

    public void setUsername(String username) {
        this.username = username.strip();
    }

    public void setFullName(String fullName) {
        this.fullName = fullName.strip();
    }
}
