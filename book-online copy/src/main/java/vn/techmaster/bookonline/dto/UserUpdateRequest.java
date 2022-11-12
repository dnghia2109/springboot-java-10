package vn.techmaster.bookonline.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;
import vn.techmaster.bookonline.entity.Gender;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class UserUpdateRequest implements Serializable {
    private String id;

    private String avatar;

    private MultipartFile multipartFile;

    @NotBlank(message = "Full name must not be empty")
    private String fullName;

    private String mobile;

    private Gender gender;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;

    private String homeAddress;

    private String workAddress;

    public void setFullName(String fullName) {
        if (fullName == null) {
            this.fullName = null;
        } else {
            this.fullName = fullName.strip();
        }
    }

    public void setMobile(String mobile) {
        if (mobile == null) {
            this.mobile = null;
        } else {
            this.mobile = mobile.strip();
        }
    }

    public void setHomeAddress(String homeAddress) {
        if (homeAddress == null) {
            this.homeAddress = null;
        } else {
            this.homeAddress = homeAddress.strip();
        }
    }

    public void setWorkAddress(String workAddress) {
        if (workAddress == null) {
            this.workAddress = null;
        } else {
            this.workAddress = workAddress.strip();
        }
    }
}
