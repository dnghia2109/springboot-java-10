package vn.techmaster.bookonline.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import vn.techmaster.bookonline.annotation.UniqueCategoryName;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class CategoryRequest implements Serializable {
    private String id;

    @NotBlank(message = "Name must not be empty")
    @UniqueCategoryName(message = "This name is used") // Custom annotation
    private String name;

    public CategoryRequest(String id, String name) {
        this.id = id;
        this.name = name.strip();
    }

    public void setName(String name) {
        this.name = name.strip();
    }
}
