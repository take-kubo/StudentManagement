package raisetech.StudentManagement.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.exception.StudentNotFoundException;
import raisetech.StudentManagement.repository.StudentRepository;

/**
 * 受講生情報を取り扱うサービスです。
 * 受講生の検索や登録、更新処理を行います。
 *
 */
@Service
public class StudentService {

  private final StudentRepository repository;
  private final StudentConverter converter;

  @Autowired
  public StudentService(StudentRepository repository, StudentConverter converter) {
    this.repository = repository;
    this.converter = converter;
  }

  /**
   * 受講生検索です。
   * IDに紐づく受講生情報を取得したあと、その受講生に紐づく受講生コース情報を取得します。
   *
   * @param id 受講生ID
   * @return 受講生
   */
  public StudentDetail searchStudent(String id) {
    // IDで指定した受講生がデータベースに存在しない場合、例外を投げる
    Student student = repository.searchStudent(id);
    if (student == null) {
      throw new StudentNotFoundException("指定した受講生が見つかりません。");
    }

    // 受講生の検索処理
    List<StudentCourse> studentCourseList = repository.searchStudentCourseListById(student.getId());
    return new StudentDetail(student, studentCourseList);
  }

  /**
   * 受講生一覧検索です。
   * 全件検索を行うので、条件指定は行いません。
   *
   * @return 受講生一覧（全件）
   */
  public List<StudentDetail> searchStudentList() {
    List<Student> students = repository.searchStudentList();
    List<StudentCourse> studentsCourseList = repository.searchStudentCourseList();
    return converter.convertStudentDetails(students, studentsCourseList);
  }

  /**
   * 受講生コース情報一覧検索です。
   * 全件検索を行うので、条件指定は行いません。
   *
   * @return 受講生コース情報一覧（全件）
   */
  public List<StudentCourse> searchStudentsCourseList() {
    return repository.searchStudentCourseList();
  }

  /**
   *受講生詳細の登録を行います。
   *受講生と受講生コース情報を個別に登録し、受講生コース情報には受講生情報を紐づける値や日付情報とコース開始日、コース終了日と設定します。
   *
   * @param studentDetail　受講生詳細
   */
  @Transactional
  public void registerStudent(StudentDetail studentDetail) {
    Student student = studentDetail.getStudent();

    // 受講生情報をデータベースに登録
    repository.registerStudent(student);
    // 受講生コース情報をデータベースに登録
    for (StudentCourse studentCourse : studentDetail.getStudentsCourses()) {
      // 受講生コース情報の必要な値を設定
      initStudentsCourse(studentCourse, student);
      repository.registerStudentCourse(studentCourse);
    }
  }

  /**
   *受講生コース情報を登録する際の初期情報を設定する。
   * @param studentCourse 受講生コース情報
   * @param student 受講生
   */
  private void initStudentsCourse(StudentCourse studentCourse, Student student) {
    LocalDateTime now = LocalDateTime.now();

    studentCourse.setStudentId(student.getId());      // 受講生のIDを受講生コース情報に代入
    studentCourse.setCourseStartAt(now);              // 受講開始日（＝登録日）を代入
    studentCourse.setCourseEndAt(now.plusYears(1));   // 受講終了予定日（＝登録日の１年後）を代入
  }

  /**
   *受講生詳細の更新を行います。受講生と受講生コース情報をそれぞれ更新します。
   *
   * @param studentDetail 受講生詳細
   */
  @Transactional
  public void updateStudent(String id, StudentDetail studentDetail) {
    // IDで指定した受講生がデータベースに存在しない場合、例外を投げる
    Student student = repository.searchStudent(id);
    if (student == null) {
      throw new StudentNotFoundException("指定した受講生が見つかりません。");
    }

    // 受講生詳細情報の更新処理
    studentDetail.getStudent().setId(id);
    repository.updateStudent(studentDetail.getStudent());
    for (StudentCourse studentCourse : studentDetail.getStudentsCourses()) {
      repository.updateStudentCourse(studentCourse);
    }

  }

}
