package vn.techmaster.bookonline.validator;

import org.springframework.beans.factory.annotation.Autowired;
import vn.techmaster.bookonline.annotation.UniqueUsername;
import vn.techmaster.bookonline.service.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {
    @Autowired
    private UserService userService;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        return !userService.existsByUsername(username);
    }
}
