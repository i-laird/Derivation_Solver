package calculator.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import calculator.DTO.ApiError;
import calculator.exception.ParseError;
import calculator.exception.UserAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

class GlobalControllerExceptionHandlerTest {

  private GlobalControllerExceptionHandler handler;

  @BeforeEach
  void setUp() {
    handler = new GlobalControllerExceptionHandler();
  }

  @Test
  void handleParseError_returnsBadRequest_withExpectedMessage() {
    ResponseEntity<ApiError> response = handler.handleParseError(new ParseError("invalid token"));

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
    assertEquals("Unable to Parse expression", response.getBody().message());
  }

  @Test
  void handleUserAlreadyExists_returnsConflict_withExpectedMessage() {
    ResponseEntity<ApiError> response =
        handler.handleUserAlreadyExists(new UserAlreadyExistsException("duplicate"));

    assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
    assertEquals("User already exists", response.getBody().message());
  }

  @Test
  void handleUnsupportedOperation_returnsNotImplemented_withExpectedMessage() {
    ResponseEntity<ApiError> response =
        handler.handleUnsupportedOperation(new UnsupportedOperationException("not implemented"));

    assertEquals(HttpStatus.NOT_IMPLEMENTED, response.getStatusCode());
    assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
    assertEquals("This derivative rule is not yet implemented", response.getBody().message());
  }

  @Test
  void handleException_returnsInternalServerError_withGenericMessage() {
    ResponseEntity<ApiError> response =
        handler.handleException(new RuntimeException("unexpected failure"));

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
    assertEquals("An internal server error occurred", response.getBody().message());
  }

  @Test
  void handleNotFound_doesNotThrow_andHasResponseStatusNotFound() throws Exception {
    handler.handleNotFound();

    org.springframework.web.bind.annotation.ResponseStatus status =
        GlobalControllerExceptionHandler.class
            .getMethod("handleNotFound")
            .getAnnotation(org.springframework.web.bind.annotation.ResponseStatus.class);
    assertEquals(HttpStatus.NOT_FOUND, status.value());
  }
}
