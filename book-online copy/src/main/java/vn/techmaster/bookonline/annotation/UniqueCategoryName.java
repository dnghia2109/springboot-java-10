package vn.techmaster.bookonline.annotation;

import vn.techmaster.bookonline.validator.UniqueCategoryNameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = UniqueCategoryNameValidator.class)
@Documented
public @interface UniqueCategoryName {
    String message() default "Invalid";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
