package raisetech.StudentManagement.data;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponse(LocalDateTime timestamp, String path, int status,
                            List<ErrorDetail> errors) {

}
