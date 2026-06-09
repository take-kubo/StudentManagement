package raisetech.StudentManagement.data;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentsCourses {

  @Size(min = 36, max = 36, message = "UUIDは３６桁です。")
  String id;

  @Size(min = 36, max = 36, message = "UUIDは３６桁です。")
  String studentId;

  @NotNull
  @Size(max = 50, message = "コース名は０文字以上５０文字以下です。")
  String courseName;

  LocalDateTime courseStartAt;

  LocalDateTime courseEndAt;

}
