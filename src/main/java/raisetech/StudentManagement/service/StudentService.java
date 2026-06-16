package raisetech.StudentManagement.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.domain.StudentDetail;
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

  public Student searchStudent(String id) {
    return repository.searchStudent(id);
  }

  public List<StudentsCourses> searchStudentCourseList(String id) {
    return repository.searchStudentCourses(id);
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

    // 受講開始日（＝登録日）を代入
    studentsCourses.setCourseStartAt(LocalDateTime.now());

    // 受講終了予定日（＝登録日の１年後）を代入
    studentsCourses.setCourseEndAt(LocalDateTime.now().plusYears(1));

    // リポジトリを呼び出してデータベースに登録
    repository.registerStudent(student);
    repository.registerStudentCourse(studentsCourses);
  }

  public void updateStudentInfo(StudentDetail studentDetail) {
    repository.updateStudent(studentDetail.getStudent());
    for (StudentsCourses studentCourse : studentDetail.getStudentsCourses()) {
      repository.updateStudentCourse(studentCourse);
    }
  }

}
