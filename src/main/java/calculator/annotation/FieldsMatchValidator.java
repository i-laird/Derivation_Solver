package calculator.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

public class FieldsMatchValidator implements ConstraintValidator<FieldsMatch, Object> {
  private String field;
  private String fieldMatch;
  private String message;

  @Override
  public void initialize(FieldsMatch constraintAnnotation) {
    this.field = constraintAnnotation.field();
    this.fieldMatch = constraintAnnotation.fieldMatch();
    this.message = constraintAnnotation.message();
  }

  @Override
  public boolean isValid(Object value, ConstraintValidatorContext context) {
    Object fieldValue = new BeanWrapperImpl(value).getPropertyValue(field);
    Object fieldMatchValue = new BeanWrapperImpl(value).getPropertyValue(fieldMatch);
    boolean valid;

    if (fieldValue != null) {
      valid = fieldValue.equals(fieldMatchValue);
    } else {
      valid = fieldMatchValue == null;
    }

    if (!valid) {
      context
          .buildConstraintViolationWithTemplate(this.message) // setting the custom message
          .addPropertyNode(this.field) // setting property path
          .addConstraintViolation() // creating the new constraint violation
          .disableDefaultConstraintViolation() // disabling the default constraint violation
      ;
    }

    return valid;
  }
}
