package raisetech.StudentManagement.exception;

import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import raisetech.StudentManagement.data.ErrorDetail;
import raisetech.StudentManagement.service.ErrorResponseBuilder;

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

    List<ErrorDetail> errorDetails = new ArrayList<>();

    for (ObjectError objectError : e.getBindingResult().getGlobalErrors()) {
      ErrorDetail errorDetail = new ErrorDetail(objectError.getObjectName(),
          objectError.getDefaultMessage());
      errorDetails.add(errorDetail);
    }

    for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
      ErrorDetail errorDetail = new ErrorDetail(fieldError.getField(),
          fieldError.getDefaultMessage());
      errorDetails.add(errorDetail);
    }

    return ResponseEntity.status(400)
        .body(new ErrorResponseBuilder().build(400, request, errorDetails));
  }

}
