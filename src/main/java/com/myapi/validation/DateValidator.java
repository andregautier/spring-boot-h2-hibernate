package com.myapi.validation;

import static com.myapi.validation.ValidatorUtils.customMessageForValidation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

public class DateValidator implements ConstraintValidator<DateChecker, String> {
  private String pattern;

  @Override
  public void initialize(DateChecker constraintAnnotation) {
    this.pattern = constraintAnnotation.pattern();
  }

  @Override
  public boolean isValid(String date, ConstraintValidatorContext context) {
    try {
      context.disableDefaultConstraintViolation();

      if (StringUtils.isBlank(date)) {
        customMessageForValidation(context, "The date of birth is required");
        return false;
      }

      final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
      LocalDateTime time = LocalDate.parse(date, formatter).atStartOfDay();

      if (time == null) {
        customMessageForValidation(context, "date is not valid");
        return false;
      }

      return time != null;
    } catch (Exception e) {
      customMessageForValidation(context, e.getMessage());
      return false;
    }
  }
}