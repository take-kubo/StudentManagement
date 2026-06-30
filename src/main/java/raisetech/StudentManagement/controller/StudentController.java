package raisetech.StudentManagement.controller;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList() {
    List<Student> students = service.searchStudentList();
    List<StudentCourse> studentsCourses = service.searchStudentsCourseList();
    return converter.convertStudentDetails(students, studentsCourses);
  }

  @GetMapping("/studentsCourseList")
  public List<StudentCourse> getStudentsCourseList() {
    return service.searchStudentsCourseList();
  }

  @GetMapping("/newStudent")
  public String newStudent(Model model) {
    model.addAttribute("studentDetail", new StudentDetail());
    return "registerStudent";
  }

  @GetMapping("/updateStudent/{id}")
  public String updateStudent(@PathVariable String id, Model model) {
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(service.searchStudent(id));
    studentDetail.setStudentsCourses(service.searchStudentCourseList(id));
    model.addAttribute("studentDetail", studentDetail);
    return "updateStudent";
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

    return "redirect:/studentList";
  }

  @PostMapping("/updateStudent")
  public String updateStudent(@ModelAttribute @Valid StudentDetail studentDetail,
      BindingResult result) {

    if (result.hasErrors()) {
      return "updateStudent";
    }

    List<StudentCourse> studentsCoursesList = studentDetail.getStudentsCourses();

    if (studentsCoursesList == null) {
      studentsCoursesList = new ArrayList<>();
    }

    if (studentsCoursesList.isEmpty()) {
      studentsCoursesList.add(new StudentCourse());
    }

    service.updateStudentInfo(studentDetail);

    return "redirect:/studentList";
  }
}
