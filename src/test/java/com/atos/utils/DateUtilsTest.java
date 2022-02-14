package com.atos.utils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DateUtilsTest {

  @Test
  public void shouldBeAdult() {

    LocalDate now = LocalDate.now();
    LocalDate testDate = now.minusYears(18);

    boolean isAdult = DateUtils.isAdult(testDate);

    assertTrue(isAdult);
  }

  @Test
  public void shoulBeAdult2() {

    LocalDate now = LocalDate.now();
    LocalDate testDate = now.minusYears(18).plusDays(1);

    boolean isAdult = DateUtils.isAdult(testDate);

    assertFalse(isAdult);
  }

  @Test
  public void shouldNoBeAdult() {

    LocalDate now = LocalDate.now();
    LocalDate testDate = now.minusYears(18).minusDays(1);

    boolean isAdult = DateUtils.isAdult(testDate);

    assertTrue(isAdult);
  }

}