package raisetech.StudentManagement.controller;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.service.StudentService;

@RestController
public class StudentController {

  private final StudentService service;
  private final StudentConverter converter;

  @Autowired
  public StudentController(StudentService service, StudentConverter converter) {
    this.service = service;
    this.converter = converter;
  }

  @GetMapping("/students")
  public List<StudentDetail> getStudentList() {
    List<Student> students = service.searchStudentList();
    List<StudentCourse> studentsCourses = service.searchStudentsCourseList();
    return converter.convertStudentDetails(students, studentsCourses);
  }

  @GetMapping("/studentsCourses")
  public List<StudentCourse> getStudentsCourseList() {
    return service.searchStudentsCourseList();
  }

  @GetMapping("/newStudent")
  public String newStudent(Model model) {
    model.addAttribute("studentDetail", new StudentDetail());
    return "registerStudent";
  }

  @PostMapping("/registerStudent")
  public String registerStudent(@ModelAttribute @Valid StudentDetail studentDetail,
      BindingResult result) {

    if (result.hasErrors()) {
      return "registerStudent";
    }

    Student student = studentDetail.getStudent();
    List<StudentCourse> studentsCoursesList = studentDetail.getStudentsCourses();

    if (studentsCoursesList == null) {
      studentsCoursesList = new ArrayList<>();
    }

    if (studentsCoursesList.isEmpty()) {
      studentsCoursesList.add(new StudentCourse());
    }

    /*
     studentsCoursesList.getFirst()について：
     このプロジェクトはJava21で開発しているので、ListにgetFirst()が実装されています
     get(0)にするとIntelliJが警告をだすので、getFirst()を使っています
    */
    service.registerStudentInfo(student, studentsCoursesList.getFirst());

    return "redirect:/students";
  }

  @PutMapping("/students/{id}")
  public ResponseEntity<String> updateStudent(
      @PathVariable("id") String id, @RequestBody @Valid StudentDetail studentDetail) {

    studentDetail.getStudent().setId(id);

    List<StudentCourse> studentsCoursesList = studentDetail.getStudentsCourses();

    if (studentsCoursesList == null) {
      studentsCoursesList = new ArrayList<>();
    }

    if (studentsCoursesList.isEmpty()) {
      studentsCoursesList.add(new StudentCourse());
    }

    if (service.updateStudentInfo(studentDetail)) {
      return ResponseEntity.status(HttpStatus.OK).body("Update success");
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");
    }

  }
}
