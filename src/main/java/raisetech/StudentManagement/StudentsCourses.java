package raisetech.StudentManagement;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentsCourses {

  String id;
  String studentId;
  String courseName;
  LocalDateTime courseStartAt;
  LocalDateTime courseEndAt;

}
