package raisetech.StudentManagement.service;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.repository.StudentRepository;

@Service
public class StudentService {

  private final StudentRepository repository;

  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  public List<Student> searchStudentList() {
    return repository.searchStudents();
  }

  public List<StudentsCourses> searchStudentsCourseList() {
    return repository.searchStudentsCourses();
  }

  public void registerStudentInfo(Student student, StudentsCourses studentsCourses) {

    // 受講生情報登録用のUUID生成
    String studentsUuid = UUID.randomUUID().toString();
    student.setId(studentsUuid);

    // 受講生コース情報登録用のUUID生成
    String studentsCoursesUuid = UUID.randomUUID().toString();
    studentsCourses.setId(studentsCoursesUuid);

    // 受講生のIDを受講生コース情報に代入
    studentsCourses.setStudentId(student.getId());

    // リポジトリを呼び出してデータベースに登録
    repository.registerStudent(student);
    repository.registerStudentCourse(studentsCourses);
  }
}
