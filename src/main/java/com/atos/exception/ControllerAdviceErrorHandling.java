package com.atos.exception;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class ControllerAdviceErrorHandling {


  @ExceptionHandler(DataAccessException.class)
  public ResponseEntity handle(DataAccessException ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
  }

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  ValidationFailedResponse onConstraintValidationException(ConstraintViolationException e) {
    ValidationFailedResponse error = new ValidationFailedResponse();
    for (ConstraintViolation violation : e.getConstraintViolations()) {
      error.getValidationErrors()
          .add(new ViolationErrors(violation.getPropertyPath().toString(), violation.getMessage()));
    }
    return error;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  ValidationFailedResponse onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    ValidationFailedResponse error = new ValidationFailedResponse();
    for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
      error.getValidationErrors()
          .add(new ViolationErrors(fieldError.getField(), fieldError.getDefaultMessage()));
    }
    return error;
  }
}

