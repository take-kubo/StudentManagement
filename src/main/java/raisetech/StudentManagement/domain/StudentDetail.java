package raisetech.StudentManagement.domain;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;

@Getter
@Setter
@NoArgsConstructor
public class StudentDetail {

  @Valid
  private Student student = new Student();

  @Valid
  private List<StudentsCourses> studentsCourses = new ArrayList<>();

}
