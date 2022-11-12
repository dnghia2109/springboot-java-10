package vn.techmaster.bookonline.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import vn.techmaster.bookonline.entity.Author;
import vn.techmaster.bookonline.entity.Category;
import vn.techmaster.bookonline.entity.Publisher;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
public class BookRequest implements Serializable {
    private String id;

    @NotBlank(message = "Name must not be empty")
    private String name;

    private Integer publishedYear;

    @NotNull(message = "Pages must not be null")
    @Positive(message = "Pages must be a positive number")
    private Integer pages;

    @NotNull(message = "Quantity must not be null")
    @PositiveOrZero(message = "Quantity must be 0 or a positive number")
    private Integer quantity;

    private String thumbnail;

    private MultipartFile multipartFile;

    private String description;

    @PositiveOrZero(message = "Price must be 0 or a positive number")
    private Long price;

    private Set<Author> authors;

    private Set<Category> categories;

    private Publisher publisher;

    public BookRequest() {
        this.categories = new LinkedHashSet<>();
        this.authors = new LinkedHashSet<>();
    }

    public void setName(String name) {
        this.name = name.strip();
    }

    public void setDescription(String description) {
        this.description = description.strip();
    }
}
