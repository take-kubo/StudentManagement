package raisetech.StudentManagement.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;

@Mapper
public interface StudentRepository {

  @Select("SELECT * FROM students")
  List<Student> searchStudents();

  @Select("SELECT * FROM students_courses")
  List<StudentsCourses> searchStudentsCourses();

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
      "VALUES(#{studentsCourses.id}, #{studentsCourses.studentId}, #{studentsCourses.courseName},"
          + " #{studentsCourses.courseStartAt}, #{studentsCourses.courseEndAt})"})
  @SelectKey(
      statement = "SELECT UUID()",
      keyProperty = "studentsCourses.id",
      before = true,
      resultType = String.class
  )
  void registerStudentCourse(@Param("studentsCourses") StudentsCourses studentsCourses);

}
