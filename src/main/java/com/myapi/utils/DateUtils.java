package com.myapi.utils;

import java.time.LocalDate;
import java.time.Period;

public class DateUtils {

  public static boolean isAdult(LocalDate dateOfBirth) {
    Period p = Period.between(dateOfBirth, LocalDate.now());
    return (p.getYears() >= 18);
  }
}
