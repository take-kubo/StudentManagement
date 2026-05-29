package raisetech.StudentManagement.controller.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.domain.StudentDetail;

@Slf4j
@Component
public class StudentConverter {

  public List<StudentDetail> convertStudentDetails(List<Student> students,
      List<StudentsCourses> studentsCourses) throws NullPointerException {

    // 受講生コース情報のリストがnullでないかチェックしています
    // nullだった場合は初期化された受講生コース情報をひとつ持つリストにしています
    try {
      Objects.requireNonNull(studentsCourses);
    } catch (NullPointerException error) {
      log.error(
          "The studentsCourses list is null.",
          error);
      StudentsCourses emptyStudentsCourse = new StudentsCourses();
      studentsCourses = List.of(emptyStudentsCourse);
    }

    List<StudentDetail> studentDetails = new ArrayList<>();

    for (Student student : students) {
      StudentDetail studentDetail = new StudentDetail();
      studentDetail.setStudent(student);

      // 受講生情報のID（student.getId()）がnullでないかチェックしています
      // nullの場合はNPEを投げます
      try {
        Objects.requireNonNull(student.getId());
      } catch (NullPointerException error) {
        log.error(
            "The id field of student is null. The students table in studentmanagement database includes null id.",
            error);
        throw new NullPointerException();
      }

      List<StudentsCourses> convertStudentCourses = new ArrayList<>();
      for (StudentsCourses studentCourse : studentsCourses) {

        if (student.getId().equals(studentCourse.getStudentId())) {
          convertStudentCourses.add(studentCourse);
        }
      }

      studentDetail.setStudentsCourses(convertStudentCourses);
      studentDetails.add(studentDetail);
    }

    return studentDetails;

  }

}
