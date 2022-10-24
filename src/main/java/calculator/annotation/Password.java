package calculator.annotation;

import java.lang.annotation.*;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = PasswordConstraintsValidator.class)
public @interface Password {

  String message() default "Invalid password!";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
