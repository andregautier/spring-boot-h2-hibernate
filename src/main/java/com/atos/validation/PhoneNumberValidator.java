package com.atos.validation;


import static com.atos.validation.ValidatorUtils.customMessageForValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

// Very basic phone number checker, just to show that an undefined or empty
// phone number is accepted, but checked when specified

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumberChecker, String> {

  private String pattern;

  @Override
  public void initialize(PhoneNumberChecker constraintAnnotation) {
  }

  @Override
  public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {

    context.disableDefaultConstraintViolation();

    if (StringUtils.isBlank(phoneNumber)) {
      return true;
    }

    if (!phoneNumber.matches("[0-9]+")) {
      customMessageForValidation(context,
          "Invalid phone number, it can only contain numbers");
      return false;
    }

    return true;
  }
}