package vn.techmaster.bookonline.validator;

import org.springframework.beans.factory.annotation.Autowired;
import vn.techmaster.bookonline.annotation.UniqueCategoryName;
import vn.techmaster.bookonline.service.CategoryService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueCategoryNameValidator implements ConstraintValidator<UniqueCategoryName, String> {
    @Autowired
    private CategoryService categoryService;

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        return !categoryService.existsByName(name);
    }
}
