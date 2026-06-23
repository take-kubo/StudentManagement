package raisetech.StudentManagement.data;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@NoArgsConstructor
public class StudentsCourses {

  String id = "";

  String studentId = "";

  @NotNull
  @Size(max = 50, message = "コース名は０文字以上５０文字以下です。")
  String courseName = "";

  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
  LocalDateTime courseStartAt = LocalDateTime.now();

  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
  LocalDateTime courseEndAt = LocalDateTime.now();

}
