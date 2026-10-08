package raisetech.StudentManagement.service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentCourseRegistrationStatus;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.exception.StudentCourseRegistrationStatusAlreadyExistsException;
import raisetech.StudentManagement.exception.StudentCourseRegistrationStatusNotFoundException;
import raisetech.StudentManagement.exception.StudentNotFoundException;
import raisetech.StudentManagement.repository.StudentRepository;
import raisetech.StudentManagement.service.converter.StudentConverter;

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
   * 受講生の名前での絞り込み検索です。
   *
   * @param name 受講生の名前
   * @return 名前で絞り込まれた受講生詳細のリスト
   */
  public List<StudentDetail> searchStudentListByName(String name) {
    List<Student> students = repository.searchStudentListByName(name);
    List<StudentCourse> studentsCourseList = repository.searchStudentCourseList();
    return converter.convertStudentDetails(students, studentsCourseList);
  }

  /**
   * 受講生の名前のふりがなで絞り込み検索です。
   *
   * @param furigana 受講生の名前のふりがな
   * @return 名前のふりがなで絞り込まれた受講生詳細リスト
   */
  public List<StudentDetail> searchStudentListFilteredByFurigana(String furigana) {
    List<Student> students = repository.searchStudentListFilteredByFurigana(furigana);
    List<StudentCourse> studentsCourseList = repository.searchStudentCourseList();
    return converter.convertStudentDetails(students, studentsCourseList);
  }

  /**
   * 受講生のニックネームでの絞り込み検索です。
   *
   * @param nickname 受講生のニックネーム
   * @return ニックネームで絞り込まれた受講生詳細リスト
   */
  public List<StudentDetail> searchStudentListFilteredByNickname(String nickname) {
    List<Student> students = repository.searchStudentListFilteredByNickname(nickname);
    List<StudentCourse> studentsCourseList = repository.searchStudentCourseList();
    return converter.convertStudentDetails(students, studentsCourseList);
  }

  /**
   * 受講生のメールアドレスでの絞り込み検索です。
   *
   * @param email 受講生のメールアドレス
   * @return メールアドレスで絞り込まれた受講生詳細リスト
   */
  public List<StudentDetail> searchStudentListFilteredByEmail(String email) {
    List<Student> students = repository.searchStudentListFilteredByEmail(email);
    List<StudentCourse> studentsCourseList = repository.searchStudentCourseList();
    return converter.convertStudentDetails(students, studentsCourseList);
  }

  /**
   * 受講生の居住地域での絞り込み検索です。
   *
   * @param address 受講生の居住地域
   * @return 居住地域で絞り込まれた受講生詳細リスト
   */
  public List<StudentDetail> searchStudentListFilteredByAddress(String address) {
    List<Student> students = repository.searchStudentListFilteredByAddress(address);
    List<StudentCourse> studentsCourseList = repository.searchStudentCourseList();
    return converter.convertStudentDetails(students, studentsCourseList);
  }

  /**
   * 受講生のジェンダーでの絞り込み検索です。
   *
   * @param gender 受講生のジェンダー
   * @return ジェンダーで絞り込まれた受講生詳細リスト
   */
  public List<StudentDetail> searchStudentListFilteredByGender(String gender) {
    List<Student> students = repository.searchStudentListFilteredByGender(gender);
    List<StudentCourse> studentsCourseList = repository.searchStudentCourseList();
    return converter.convertStudentDetails(students, studentsCourseList);
  }

  /**
   * 受講生の備考での絞り込み検索です。
   *
   * @param remark 受講生の備考
   * @return 備考で絞り込まれた受講生詳細リスト
   */
  public List<StudentDetail> searchStudentListFilteredByRemark(String remark) {
    List<Student> students = repository.searchStudentListFilteredByRemark(remark);
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
      Clock clock = Clock.systemDefaultZone();
      initStudentsCourse(studentCourse, student, clock);
      repository.registerStudentCourse(studentCourse);
    }
  }

  /**
   *受講生コース情報を登録する際の初期情報を設定する。
   * @param studentCourse 受講生コース情報
   * @param student 受講生
   */
  void initStudentsCourse(StudentCourse studentCourse, Student student, Clock clock) {
    LocalDateTime now = LocalDateTime.now(clock);

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

  /**
   * 受講生コース申込状況の一覧検索です。
   * 全件検索を行うので、条件指定は行いません。
   *
   * @return 受講生コース申込状況の一覧（全件）
   */
  public List<StudentCourseRegistrationStatus> searchStudentCourseRegistrationStatusList() {
    return repository.searchStudentCourseRegistrationStatus();
  }

  /**
   * 受講生コース申込状況の一件検索です。
   * IDに紐づく受講生コース申込状況を取得します。
   *
   * @param id 受講生コース申込状況ID
   * @return 受講生コース状況
   */
  public StudentCourseRegistrationStatus searchStudentCourseRegistrationStatusById(String id) {

    StudentCourseRegistrationStatus oldStatus = repository.searchStudentCourseRegistrationStatusById(
        id);
    if (oldStatus == null) {
      throw new StudentCourseRegistrationStatusNotFoundException(
          "指定したIDを持つ受講生コース申込状況は存在していません。");
    }

    return repository.searchStudentCourseRegistrationStatusById(id);
  }

  /**
   * 受講生コース申込状況の登録です。
   *
   * @param newStatus 受講生コース申込状況
   */
  @Transactional
  public void registerStudentCourseRegistrationStatus(StudentCourseRegistrationStatus newStatus) {

    StudentCourseRegistrationStatus oldStatus = repository.searchStudentCourseRegistrationStatusById(
        newStatus.getId());

    if (newStatus.getId().equals(oldStatus.getId())) {
      throw new StudentCourseRegistrationStatusAlreadyExistsException(
          "指定したIDをもつ受講生コース申込状況はすでに存在しています。");
    }

    repository.registerStudentCourseRegistrationStatus(newStatus);
  }

  /**
   * 受講生コース申込状況の更新です。
   *
   * @param id 受講生コース申込状況ID
   * @param newStatus 受講生コース申込状況
   */
  @Transactional
  public void updateStudentCourseRegistrationStatus(String id,
      StudentCourseRegistrationStatus newStatus) {

    StudentCourseRegistrationStatus oldStatus = repository.searchStudentCourseRegistrationStatusById(
        id);
    if (oldStatus == null) {
      throw new StudentCourseRegistrationStatusNotFoundException(
          "指定した受講生コース申込状況が見つかりません");
    }
    repository.updateStudentCourseRegistrationStatus(newStatus);
  }

}
