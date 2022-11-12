package vn.techmaster.bookonline.validator;

import org.springframework.beans.factory.annotation.Autowired;
import vn.techmaster.bookonline.annotation.UniquePublisherName;
import vn.techmaster.bookonline.service.PublisherService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniquePublisherNameValidator implements ConstraintValidator<UniquePublisherName, String> {
    @Autowired
    private PublisherService publisherService;

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        return !publisherService.existsByName(name);
    }
}
