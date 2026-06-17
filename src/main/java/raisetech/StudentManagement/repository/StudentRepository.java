package raisetech.StudentManagement.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.SelectKey;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;

@Mapper
public interface StudentRepository {

  @Select("SELECT * FROM students")
  List<Student> searchStudents();

  @Select("SELECT * FROM students_courses")
  List<StudentCourse> searchStudentsCourses();

  @Select("SELECT * FROM students WHERE id = #{id}")
  Student searchStudent(@Param("id") String id);

  @Select("SELECT * FROM students_courses WHERE student_id = #{id}")
  List<StudentsCourses> searchStudentCourses(@Param("id") String id);

  @Insert({
      "INSERT INTO students(id, name, furigana, nickname, email, address, age, gender, remark, is_deleted)",
      "VALUES(#{student.id}, #{student.name}, #{student.furigana}, #{student.nickname}, #{student.email}, "
          + "#{student.address}, #{student.age}, #{student.gender}, #{student.remark}, false)"})
  @SelectKey(
      statement = "SELECT UUID()",
      keyProperty = "student.id",
      before = true,
      resultType = String.class
  )
  void registerStudent(@Param("student") Student student);

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

  @Update(
      "UPDATE students SET id=#{id}, name=#{name}, furigana=#{furigana}, nickname=#{nickname}, email=#{email},"
          + " address=#{address}, age=#{age}, gender=#{gender}, remark=#{remark}, deleted=#{deleted} "
          + "WHERE id=#{id}")
  void updateStudent(Student student);

  @Update(
      "UPDATE students_courses "
          + "SET course_name=#{courseName}, course_start_at=#{courseStartAt}, course_end_at=#{courseEndAt}"
          + "WHERE id=#{id}")
  void updateStudentCourse(StudentsCourses studentsCourses);
}
