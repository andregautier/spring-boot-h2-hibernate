package com.myapi.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

public class EnumNamePatternValidator implements ConstraintValidator<EnumNamePatternChecker, String> {
  private Pattern pattern;

  @Override
  public void initialize(EnumNamePatternChecker annotation) {
    try {
      pattern = Pattern.compile(annotation.regexp());
    } catch (PatternSyntaxException e) {
      throw new IllegalArgumentException("Given regex is invalid", e);
    }
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {

    context.disableDefaultConstraintViolation();

    if (StringUtils.isBlank(value)) {
      return true;
    }

    Matcher m = pattern.matcher(value);

    if (!m.matches()) {
      ValidatorUtils.customMessageForValidation(context, "Invalid gender, it must be MALE or FEMALE");
      return false;
    }

    return true;
  }
}