package calculator.controller;

import calculator.DTO.ApiError;
import calculator.exception.ParseError;
import calculator.exception.UserNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class GlobalControllerExceptionHandler {

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
}
