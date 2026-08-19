package raisetech.StudentManagement.exception;

import org.springframework.http.HttpStatus;
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

  @ExceptionHandler(StudentNotFoundException.class)
  public ResponseEntity<String> studentNotFoundExceptionHandler(StudentNotFoundException ex) {
    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body("指定した受講生が見つかりません。IDを確認してください。");
  }
  
}
