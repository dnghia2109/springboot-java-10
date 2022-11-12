package vn.techmaster.bookonline.exception;

import org.springframework.http.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ErrorMessage {
    private HttpStatus status;
    private String message;
}
