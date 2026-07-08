package raisetech.StudentManagement.exception;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import raisetech.StudentManagement.data.ErrorDTO;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(NullPointerException.class)
  public ResponseEntity<String> nullPointerExceptionHandler() {
    return ResponseEntity
        .internalServerError()
        .body("システムエラーが発生しました。また再度アクセスしてください。");
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<List<ErrorDTO>> methodArgumentNotValidExceptionHandler(
      MethodArgumentNotValidException e) {

    List<ErrorDTO> errors = new ArrayList<>();

    for (FieldError fieldError : e.getFieldErrors()) {
      ErrorDTO error = new ErrorDTO(fieldError.getField(), fieldError.getDefaultMessage(),
          fieldError.getRejectedValue());
      errors.add(error);
    }

    return ResponseEntity.badRequest().body(errors);
  }

}
