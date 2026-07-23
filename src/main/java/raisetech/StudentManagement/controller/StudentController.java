package raisetech.StudentManagement.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.FieldErrorDTO;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.data.SuccessDTO;
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

  @GetMapping("/courses")
  public List<StudentCourse> getStudentsCourseList() {
    return service.searchStudentsCourseList();
  }

  @PostMapping("/students")
  public ResponseEntity<Object> registerStudent(@RequestBody @Valid StudentDetail studentDetail,
      HttpServletRequest request) {

    if (studentDetail.getStudentsCourses() == null || studentDetail.getStudentsCourses()
        .isEmpty()) {
      studentDetail.setStudentsCourses(new LinkedList<>());
      studentDetail.getStudentsCourses().add(new StudentCourse());
    }

    /*
     studentsCoursesList.getFirst()について：
     このプロジェクトはJava21で開発しているので、ListにgetFirst()が実装されています
     get(0)にするとIntelliJが警告をだすので、getFirst()を使っています
    */
    if (!service.registerStudentInfo(studentDetail.getStudent(),
        studentDetail.getStudentsCourses().getFirst())) {

      Map<String, Object> errorInfo = new LinkedHashMap<>();
      errorInfo.put("timestamp", LocalDateTime.now().toString());
      errorInfo.put("status", 400);
      errorInfo.put("path", request.getRequestURI());

      ArrayList<Object> errors = new ArrayList<>();
      FieldErrorDTO error = new FieldErrorDTO("", "Illegal Request", "");
      errors.add(error);
      errorInfo.put("errors", errors);

      return ResponseEntity.status(400).body(errorInfo);
    }

    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(studentDetail.getStudent().getId()).toUri();
    SuccessDTO successDTO = new SuccessDTO("Register success.", studentDetail.getStudent().getId());
    return ResponseEntity.status(HttpStatus.CREATED).location(uri).body(successDTO);

  }

  @PutMapping("/students/{id}")
  public ResponseEntity<Object> updateStudent(
      @PathVariable("id") String id, @RequestBody @Valid StudentDetail studentDetail,
      HttpServletRequest request) {

    studentDetail.getStudent().setId(id);

    if (studentDetail.getStudentsCourses() == null || studentDetail.getStudentsCourses()
        .isEmpty()) {
      studentDetail.setStudentsCourses(new ArrayList<>());
      studentDetail.getStudentsCourses().add(new StudentCourse());
    }

    if (!service.updateStudentInfo(studentDetail)) {

      Map<String, Object> errorInfo = new LinkedHashMap<>();
      errorInfo.put("timestamp", LocalDateTime.now().toString());
      errorInfo.put("status", 404);
      errorInfo.put("path", request.getRequestURI());

      ArrayList<Object> errors = new ArrayList<>();
      FieldErrorDTO error = new FieldErrorDTO("id", "Not Found", id);
      errors.add(error);
      errorInfo.put("errors", errors);

      return ResponseEntity.status(404).body(errorInfo);
    }

    SuccessDTO successDTO = new SuccessDTO("Update success.", studentDetail.getStudent().getId());
    return ResponseEntity.status(HttpStatus.OK).body(successDTO);

  }
}
