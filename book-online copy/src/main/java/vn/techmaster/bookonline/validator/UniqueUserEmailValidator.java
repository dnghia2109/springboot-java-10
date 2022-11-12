package vn.techmaster.bookonline.validator;

import org.springframework.beans.factory.annotation.Autowired;
import vn.techmaster.bookonline.annotation.UniquePublisherName;
import vn.techmaster.bookonline.annotation.UniqueUserEmail;
import vn.techmaster.bookonline.service.PublisherService;
import vn.techmaster.bookonline.service.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUserEmailValidator implements ConstraintValidator<UniqueUserEmail, String> {
    @Autowired
    private UserService userService;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return !userService.existsByEmail(email);
    }
}
