package raisetech.StudentManagement.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(NullPointerException.class)
  public ResponseEntity<String> nullPointerExceptionHandler() {
    return ResponseEntity
        .internalServerError()
        .body("システムエラーが発生しました。また再度アクセスしてください。");
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<String> methodArgumentNotValidExceptionHandler() {
    return ResponseEntity
        .badRequest()
        .body("不正なリクエストです。入力チェックを通過しませんでした。");
  }

}
