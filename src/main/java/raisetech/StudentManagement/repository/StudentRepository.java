package raisetech.StudentManagement.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;

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

  @Select("SELECT * FROM students WHERE deleted = false")
  List<Student> searchStudents();

  /**
   * 受講生のコース情報の全件検索です。
   *
   * @return 受講生コース情報（全件）
   */
  @Select("SELECT * FROM students_courses")
  List<StudentCourse> searchStudentsCourses();

  /**
   * 受講生の検索を行います。
   *
   * @param id 受講生ID
   * @return 受講生
   */
  @Select("SELECT * FROM students WHERE id = #{id} AND deleted = false")
  Student searchStudent(@Param("id") String id);

  /**
   * 受講生IDに紐づく受講生コース情報を検索します。
   *
   * @param studentId 受講生ID
   * @return 受講生IDに紐づく受講生コース情報
   */
  @Select("Select * From students_courses WHERE student_id = #{studentId}")
  List<StudentCourse> searchStudentCourses(@Param("studentId") String studentId);

  /**
   * 受講生を新規登録します。IDに関しては自動採番を行う。
   *
   * @param student 受講生
   */
  @Insert({
      "INSERT INTO students(id, name, furigana, nickname, email, address, age, gender, remark, deleted)",
      "VALUES(#{student.id}, #{student.name}, #{student.furigana}, #{student.nickname}, #{student.email}, "
          + "#{student.address}, #{student.age}, #{student.gender}, #{student.remark}, false)"})
  @SelectKey(
      statement = "SELECT UUID()",
      keyProperty = "student.id",
      before = true,
      resultType = String.class
  )
  void registerStudent(@Param("student") Student student);

  /**
   *受講生コース情報を新規登録します。IDに関しては自動採番を行う。
   *
   * @param studentCourse 受講生コース情報
   */
  @Insert({
      "INSERT INTO students_courses(id, student_id, course_name, course_start_at, course_end_at)",
      "VALUES(#{studentCourse.id}, #{studentCourse.studentId}, #{studentCourse.courseName},"
          + " #{studentCourse.courseStartAt}, #{studentCourse.courseEndAt})"})
  @SelectKey(
      statement = "SELECT UUID()",
      keyProperty = "studentCourse.id",
      before = true,
      resultType = String.class
  )
  void registerStudentCourse(@Param("studentCourse") StudentCourse studentCourse);

  /**
   * 受講生を更新します。
   *
   * @param student 受講生
   */
  @Update(
      "UPDATE students SET id=#{id}, name=#{name}, furigana=#{furigana}, nickname=#{nickname}, email=#{email},"
          + " address=#{address}, age=#{age}, gender=#{gender}, remark=#{remark}, deleted=#{deleted} "
          + "WHERE id=#{id}")
  void updateStudent(Student student);

  /**
   * 受講生コース情報のコース名を更新します。
   *
   * @param studentsCourses 受講生コース情報
   */
  @Update(
      "UPDATE students_courses "
          + "SET course_name=#{courseName}, course_start_at=#{courseStartAt}, course_end_at=#{courseEndAt}"
          + "WHERE id=#{id}")
  void updateStudentCourse(StudentCourse studentsCourses);
}
