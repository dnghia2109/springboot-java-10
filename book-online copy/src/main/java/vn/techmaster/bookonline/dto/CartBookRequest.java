package vn.techmaster.bookonline.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Positive;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class CartBookRequest implements Serializable {
    private String id;

    @Positive(message = "Quantity must be positive")
    private Integer quantity;
}
