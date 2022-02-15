package com.myapi.exception;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidationFailedResponse {
  private List<ViolationErrors> validationErrors = new ArrayList<>();
}