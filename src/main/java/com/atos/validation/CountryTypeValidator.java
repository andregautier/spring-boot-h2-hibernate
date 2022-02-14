package com.atos.validation;

import static com.atos.validation.ValidatorUtils.customMessageForValidation;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;


public class CountryTypeValidator implements ConstraintValidator<CountryTypeChecker, String> {
  private Set<String> acceptedValues;

  @Override
  public void initialize(CountryTypeChecker annotation) {
    acceptedValues = Stream.of(annotation.enumClass().getEnumConstants())
        .map(Enum::name)
        .collect(Collectors.toSet());
  }

  @Override
  public boolean isValid(String country, ConstraintValidatorContext context) {

    context.disableDefaultConstraintViolation();

    if (StringUtils.isBlank(country)) {
      customMessageForValidation(context, "The country is required");
      return false;
    }

    if (!acceptedValues.contains(country)) {
      customMessageForValidation(context, "Invalid country, The country name must be in English and uppercase");
      return false;
    }

    return true;
  }
}