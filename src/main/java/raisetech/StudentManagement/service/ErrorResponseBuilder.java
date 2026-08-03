package raisetech.StudentManagement.service;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Component;
import raisetech.StudentManagement.data.ErrorDetail;
import raisetech.StudentManagement.data.ErrorResponse;

@Component
public class ErrorResponseBuilder {

  public ErrorResponse build(int status, HttpServletRequest request,
      List<ErrorDetail> errorDetails) {
    return new ErrorResponse(LocalDateTime.now(), request.getRequestURI(), status, errorDetails);
  }

}
