package raisetech.StudentManagement.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.repository.StudentRepository;

@Service
public class StudentService {

  private final StudentRepository repository;

  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  public List<Student> searchStudentList() {

    List<Student> allStudents = repository.searchStudents();

    List<Student> studentsOver30;
    studentsOver30 = allStudents.stream()
        .filter(student -> student.getAge() > 30)
        .toList();

    return studentsOver30;
  }

  public List<StudentsCourses> searchStudentsCourseList() {

    List<StudentsCourses> allStudentsCourses = repository.searchStudentsCourses();

    List<StudentsCourses> studentsCoursesJavaCourse;
    studentsCoursesJavaCourse = allStudentsCourses.stream()
        .filter(course -> course.getCourseName().equals("Javaコース"))
        .toList();

    return studentsCoursesJavaCourse;
  }
}
