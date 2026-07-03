package raisetech.StudentManagement.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
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

  public List<StudentCourse> searchStudentsCourseList() {
    return repository.searchStudentsCourses();
  }

  public Student searchStudent(String id) {
    return repository.searchStudent(id);
  }

  public List<StudentCourse> searchStudentCourseList(String id) {
    return repository.searchStudentCourses(id);
  }

  @Transactional
  public void registerStudentInfo(Student student, StudentCourse studentCourse) {

    // 受講生情報をデータベースに登録
    repository.registerStudent(student);

    // 受講生コース情報の必要な値を設定
    studentCourse.setStudentId(student.getId());    // 受講生のIDを受講生コース情報に代入
    studentCourse.setCourseStartAt(LocalDateTime.now());    // 受講開始日（＝登録日）を代入
    studentCourse.setCourseEndAt(LocalDateTime.now().plusYears(1));   // 受講終了予定日（＝登録日の１年後）を代入

    // 受講生コース情報をデータベースに登録
    repository.registerStudentCourse(studentCourse);
  }

  @Transactional
  public boolean updateStudentInfo(StudentDetail studentDetail) {

    if (repository.searchStudent(studentDetail.getStudent().getId()) != null) {
      repository.updateStudent(studentDetail.getStudent());
      for (StudentCourse studentCourse : studentDetail.getStudentsCourses()) {
        repository.updateStudentCourse(studentCourse);
      }
      return true;
    } else {
      return false;
    }


  }

}
