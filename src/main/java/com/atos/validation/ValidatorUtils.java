package com.atos.validation;

import javax.validation.ConstraintValidatorContext;

public class ValidatorUtils {
  public static void customMessageForValidation(ConstraintValidatorContext context,
      String message) {
    context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
  }
}