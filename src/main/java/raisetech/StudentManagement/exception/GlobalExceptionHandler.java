package raisetech.StudentManagement.exception;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import raisetech.StudentManagement.data.FieldErrorDTO;
import raisetech.StudentManagement.data.GlobalErrorDTO;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(NullPointerException.class)
  public ResponseEntity<String> nullPointerExceptionHandler() {
    return ResponseEntity
        .internalServerError()
        .body("システムエラーが発生しました。また再度アクセスしてください。");
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Object> methodArgumentNotValidExceptionHandler(
      MethodArgumentNotValidException e, HttpServletRequest request) {

    Map<String, Object> errorInfo = new LinkedHashMap<>();
    errorInfo.put("timestamp", LocalDateTime.now().toString());
    errorInfo.put("status", 400);
    errorInfo.put("path", request.getRequestURI());

    ArrayList<Object> errors = new ArrayList<>();

    for (ObjectError objectError : e.getBindingResult().getGlobalErrors()) {
      GlobalErrorDTO error = new GlobalErrorDTO(objectError.getObjectName(),
          objectError.getDefaultMessage());
      errors.add(error);
    }

    for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
      FieldErrorDTO error = new FieldErrorDTO(fieldError.getField(), fieldError.getDefaultMessage(),
          fieldError.getRejectedValue());
      errors.add(error);
    }

    errorInfo.put("errors", errors);

    return ResponseEntity.status(400).body(errorInfo);
  }

}
