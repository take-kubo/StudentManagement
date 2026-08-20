package raisetech.StudentManagement.controller.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import raisetech.StudentManagement.exception.TestException;

@ControllerAdvice
public class TestExceptionHandler {

  @ExceptionHandler(TestException.class)
  public ResponseEntity<String> handlerTestException(TestException ex) {
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(ex.getMessage());
  }
}
