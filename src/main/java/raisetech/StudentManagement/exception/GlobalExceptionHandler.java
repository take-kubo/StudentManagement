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
  public ResponseEntity<Object> nullPointerExceptionHandler(NullPointerException e,
      HttpServletRequest request) {

    List<ErrorDetail> errorDetails = new ArrayList<>();

    ErrorDetail errorDetail = new ErrorDetail("internal",
        "システムエラーが発生しました。再度アクセスしてください。");
    errorDetails.add(errorDetail);

    return ResponseEntity.status(500)
        .body(new ErrorResponseBuilder().build(500, request, errorDetails));

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


  @ExceptionHandler(IllegalRequestException.class)
  public ResponseEntity<Object> illegalRequestExceptionHandler(IllegalRequestException e,
      HttpServletRequest request) {

    List<ErrorDetail> errorDetails = new ArrayList<>();

    ErrorDetail errorDetail = new ErrorDetail(e.getFieldName(), e.getMessage());
    errorDetails.add(errorDetail);

    return ResponseEntity.status(500)
        .body(new ErrorResponseBuilder().build(500, request, errorDetails));

  }

}
