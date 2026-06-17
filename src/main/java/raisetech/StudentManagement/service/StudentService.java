package raisetech.StudentManagement.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

  @Transactional
  public void registerStudentInfo(Student student, StudentsCourses studentsCourses) {

    // 受講生情報をデータベースに登録
    repository.registerStudent(student);

    // 受講生コース情報の必要な値を設定
    studentsCourses.setStudentId(student.getId());    // 受講生のIDを受講生コース情報に代入
    studentsCourses.setCourseStartAt(LocalDateTime.now());    // 受講開始日（＝登録日）を代入
    studentsCourses.setCourseEndAt(LocalDateTime.now().plusYears(1));   // 受講終了予定日（＝登録日の１年後）を代入

    // 受講生コース情報をデータベースに登録
    repository.registerStudentCourse(studentsCourses);
  }
}
