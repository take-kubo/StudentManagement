package raisetech.StudentManagement.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;

@Mapper
public interface StudentRepository {

  @Select("SELECT * FROM students")
  List<Student> searchStudents();

  @Select("SELECT * FROM students_courses")
  List<StudentsCourses> searchStudentsCourses();

  @Insert({
      "INSERT INTO students(name, furigana, nickname, email, address, age, gender, remark)",
      "VALUES(#{name},#{furigana},#{nickname},#{email},#{address},#{age},#{gender},#{remark})"})
  void registerStudent(Student student);

  @Insert({
      "INSERT INTO students_courses(course_name, course_start_at, course_end_at)",
      "VALUES(#{courseName}, #{courseStartAt}, #{courseEndAt})"})
  void registerStudentCourse(StudentsCourses studentsCourses);

}
