package raisetech.StudentManagement.domain;

import jakarta.validation.Valid;
import java.util.LinkedList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;

@Getter
@Setter
@NoArgsConstructor
public class StudentDetail {

  @Valid
  private Student student = new Student();

  @Valid
  private List<StudentCourse> studentsCourses = new LinkedList<>(List.of(new StudentCourse()));

}
