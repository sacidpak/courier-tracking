package com.sacidpak.courier.tracking.exception;


import com.sacidpak.courier.tracking.data.response.ErrorResponse;
import com.sacidpak.courier.tracking.data.response.ValidationErrorResponse;
import com.sacidpak.courier.tracking.util.MessageUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public <T> ResponseEntity<Object> methodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
    var message = MessageUtil.getMessage("error.validation.failed");
    var validationErrorList = ex.getAllErrors()
                                .stream()
                                .map(objectError -> {
                                  var fieldError = (FieldError) objectError;
                                  return ValidationErrorResponse.builder()
                                                                .objectName(fieldError.getObjectName())
                                                                .field(fieldError.getField())
                                                                .code(fieldError.getCode())
                                                                .defaultMessage(fieldError.getDefaultMessage())
                                                                .rejectedValue(fieldError.getRejectedValue())
                                                                .build();
                                }).toList();

    var status = HttpStatus.BAD_REQUEST;
    var response = ErrorResponse.builder()
                                .status(status.value())
                                .isSuccess(false)
                                .error(status.getReasonPhrase())
                                .message(message)
                                .data(validationErrorList)
                                .build();

    return new ResponseEntity<>(response, status);
  }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleValidationException(NotFoundException e) {
        var status = HttpStatus.NOT_FOUND;
        var response = ErrorResponse.builder()
                .status(status.value())
                .isSuccess(false)
                .error(status.getReasonPhrase())
                .message(e.getLocalizedMessage())
                .build();
        return new ResponseEntity<>(response, status);
    }

}
