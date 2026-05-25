package raisetech.StudentManagement.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import raisetech.StudentManagement.Student;
import raisetech.StudentManagement.StudentsCourses;

@Mapper
public interface StudentRepository {

  @Select("SELECT * FROM students")
  List<Student> searchStudents();

  @Select("SELECT * FROM students_courses")
  List<StudentsCourses> searchStudentsCourses();

}
