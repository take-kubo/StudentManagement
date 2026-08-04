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

  @Transactional
  public void registerStudentInfo(StudentDetail studentDetail) {

    // 受講生情報をデータベースに登録
    repository.registerStudent(studentDetail.getStudent());

    // 受講生コース情報の必要な値を設定
    studentDetail.getStudentsCourses().getFirst()
        .setStudentId(studentDetail.getStudent().getId());    // 受講生のIDを受講生コース情報に代入
    studentDetail.getStudentsCourses().getFirst()
        .setCourseStartAt(LocalDateTime.now());    // 受講開始日（＝登録日）を代入
    studentDetail.getStudentsCourses().getFirst()
        .setCourseEndAt(LocalDateTime.now().plusYears(1));   // 受講終了予定日（＝登録日の１年後）を代入

    // 受講生コース情報をデータベースに登録
    repository.registerStudentCourse(studentDetail.getStudentsCourses().getFirst());

  }

  @Transactional
  public void updateStudentInfo(StudentDetail studentDetail) {

    repository.updateStudent(studentDetail.getStudent());

    for (StudentCourse studentCourse : studentDetail.getStudentsCourses()) {
      repository.updateStudentCourse(studentCourse);
    }

  }

}
