package vn.techmaster.bookonline.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class CommentRequest implements Serializable {
    private String content;
}
