package calculator.controller;

import calculator.DTO.ApiError;
import calculator.exception.ParseError;
import calculator.exception.UserAlreadyExistsException;
import calculator.exception.UserNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(ParseError.class)
  public ResponseEntity<ApiError> handleParseError(ParseError e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .contentType(MediaType.APPLICATION_JSON)
        .body(new ApiError("Unable to Parse expression"));
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(UserNotFound.class)
  public void handleNotFound() {
    // Nothing to do
  }

  @ExceptionHandler(UserAlreadyExistsException.class)
  public ResponseEntity<ApiError> handleUserAlreadyExists(UserAlreadyExistsException e) {
    return ResponseEntity.status(HttpStatus.CONFLICT)
        .contentType(MediaType.APPLICATION_JSON)
        .body(new ApiError("User already exists"));
  }

  @ExceptionHandler(UnsupportedOperationException.class)
  public ResponseEntity<ApiError> handleUnsupportedOperation(UnsupportedOperationException e) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
        .contentType(MediaType.APPLICATION_JSON)
        .body(new ApiError("This derivative rule is not yet implemented"));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiError> handleException(Exception e) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .contentType(MediaType.APPLICATION_JSON)
        .body(new ApiError("An internal server error occurred"));
  }
}
