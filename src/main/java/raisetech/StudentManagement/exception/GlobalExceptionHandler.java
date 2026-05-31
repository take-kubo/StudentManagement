package raisetech.StudentManagement.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(NullPointerException.class)
  public ResponseEntity<String> nullPointerExceptionHandler(NullPointerException e) {
    return ResponseEntity
        .internalServerError()
        .body("システムエラーが発生しました。また再度アクセスしてください。");
  }

}
