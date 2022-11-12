package vn.techmaster.bookonline.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.techmaster.bookonline.entity.Gender;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class AuthorRequest implements Serializable {
    private String id;

    @NotBlank(message = "Full name must not be empty")
    private String fullName;

    private Gender gender;

    private String address;

    private Integer yearOfBirth;

    private Integer yearOfDeath;

    public AuthorRequest(String id, String fullName, Gender gender, String address, Integer yearOfBirth,
                         Integer yearOfDeath) {
        this.id = id;
        this.fullName = fullName.strip();
        this.gender = gender;
        this.address = address.strip();
        this.yearOfBirth = yearOfBirth;
        this.yearOfDeath = yearOfDeath;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName.strip();
    }

    public void setAddress(String address) {
        this.address = address.strip();
    }
}
