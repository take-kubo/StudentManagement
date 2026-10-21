package raisetech.StudentManagement.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentCourseRegistrationStatus;

/**
 * 受講生テーブルと受講生コース情報テーブルと紐づくRepositoryです。
 */
@Mapper
public interface StudentRepository {

  /**
   * 受講生の全件検索を行います
   *
   * @return 受講生一覧（全件）
   */
  List<Student> searchStudentList();

  /**
   * 受講生のコース情報の全件検索です。
   *
   * @return 受講生コース情報（全件）
   */
  List<StudentCourse> searchStudentCourseList();

  /**
   * 受講生の検索を行います。
   *
   * @param id 受講生ID
   * @return 受講生
   */
  Student searchStudent(@Param("id") String id);

  /**
   * 受講生を名前で部分一致検索をします。
   *
   * @param name 受講生の名前
   * @return 受講生一覧（名前の部分一致で絞り込み）
   */
  List<Student> searchStudentListByName(@Param("name") String name);

  /**
   * 受講生の名前のふりがなで部分一致検索をします。
   *
   * @param furigana　受講生の名前のふりがな
   * @return 受講生一覧（ふりがなの部分一致で絞り込み）
   */
  List<Student> searchStudentListFilteredByFurigana(@Param("furigana") String furigana);

  /**
   * 受講生のニックネームで部分一致検索をします。
   *
   * @param nickname 受講生のニックネーム
   * @return 受講生一覧（ニックネームの部分一致で絞り込み）
   */
  List<Student> searchStudentListFilteredByNickname(@Param("nickname") String nickname);

  /**
   * 受講生のメールアドレスで部分一致検索をします。
   *
   * @param email 受講生のメールアドレス
   * @return 受講生一覧（メールアドレスの部分一致で絞り込み）
   */
  List<Student> searchStudentListFilteredByEmail(@Param("email") String email);

  /**
   * 受講生の居住地域で部分一致検索をします。
   *
   * @param address 受講生の居住地域
   * @return 受講生一覧（居住地域の部分一致で絞り込み）
   */
  List<Student> searchStudentListFilteredByAddress(@Param("address") String address);

  /**
   * 受講生のジェンダーで部分一致検索をします。
   *
   * @param gender 受講生のジェンダー
   * @return 受講生一覧（ジェンダーの部分一致で絞り込み）
   */
  List<Student> searchStudentListFilteredByGender(@Param("gender") String gender);

  /**
   * 受講生の備考で部分一致検索をします。
   *
   * @param remark 受講生の備考
   * @return 受講生一覧（備考の部分一致で絞り込み）
   */
  List<Student> searchStudentListFilteredByRemark(@Param("remark") String remark);

  /**
   * 受講生の年齢で絞り込み検索をします。
   *
   * @param minAge 年齢範囲の下限
   * @param maxAge 年齢範囲の上限
   * @return 受講生一覧（年齢の絞り込み検索で絞り込み）
   */
  List<Student> searchStudentListFilteredByAge(@Param("minAge") Integer minAge,
      @Param("maxAge") Integer maxAge);

  /**
   * 受講生コース情報の検索を行います。
   *
   * @param id 受講生コースID
   * @return 受講生コース情報
   */
  StudentCourse searchStudentCourse(@Param("id") String id);

  /**
   * 受講生IDに紐づく受講生コース情報を検索します。
   *
   * @param studentId 受講生ID
   * @return 受講生IDに紐づく受講生コース情報
   */
  List<StudentCourse> searchStudentCourseListById(@Param("studentId") String studentId);

  /**
   * 受講生を新規登録します。IDに関しては自動採番を行う。
   *
   * @param student 受講生
   */
  void registerStudent(@Param("student") Student student);

  /**
   *受講生コース情報を新規登録します。IDに関しては自動採番を行う。
   *
   * @param studentCourse 受講生コース情報
   */
  void registerStudentCourse(@Param("studentCourse") StudentCourse studentCourse);

  /**
   * 受講生を更新します。
   *
   * @param student 受講生
   */
  void updateStudent(Student student);

  /**
   * 受講生コース情報のコース名を更新します。
   *
   * @param studentCourse 受講生コース情報
   */
  void updateStudentCourse(StudentCourse studentCourse);

  /**
   * 受講生コース申込状況の全件検索を行います。
   *
   * @return 受講生コース申込状況一覧（全件）
   */
  List<StudentCourseRegistrationStatus> searchStudentCourseRegistrationStatus();

  /**
   * 受講生コース申込状況の1件検索を行います。
   *
   * @param id 受講生コース申込状況ID
   * @return 受講生コース申込状況
   */
  StudentCourseRegistrationStatus searchStudentCourseRegistrationStatusById(@Param("id") String id);

  /**
   * 受講生コース申込状況の登録を行います。
   *
   * @param studentCourseRegistrationStatus 受講生コース申込状況
   */
  void registerStudentCourseRegistrationStatus(
      @Param("studentCourseRegistrationStatus") StudentCourseRegistrationStatus studentCourseRegistrationStatus);

  /**
   * 受講生コース申込状況の更新を行います。
   *
   * @param studentCourseRegistrationStatus 受講生コース申込状況
   */
  void updateStudentCourseRegistrationStatus(
      @Param("studentCourseRegistrationStatus") StudentCourseRegistrationStatus studentCourseRegistrationStatus);

}
