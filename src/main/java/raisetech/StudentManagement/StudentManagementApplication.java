package raisetech.StudentManagement;

import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class StudentManagementApplication {

  @Autowired
  StudentRepository repository;

  public static void main(String[] args) {
    SpringApplication.run(StudentManagementApplication.class, args);
  }

  @GetMapping("/student")
  public ResponseEntity<String> getStudent(@RequestParam String name) {
    Student student = repository.searchByName(name);

    if (Objects.isNull(student)) {
      String errorMessage = "指定された名前の受講生が見つかりませんでした。";
      return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    String normalMessage = student.getName() + " " + student.getAge() + "歳";
    return new ResponseEntity<>(normalMessage, HttpStatus.OK);

  }

  @PostMapping("/student")
  public void registerStudent(@RequestParam String name, @RequestParam int age) {
    repository.registerStudent(name, age);
  }

  @PatchMapping("/student")
  public void updateStudent(@RequestParam String name, @RequestParam int age) {
    repository.updateStudent(name, age);
  }

  @DeleteMapping("/student")
  public void deleteStudent(@RequestParam String name) {
    repository.deleteStudent(name);
  }

  @GetMapping("/allStudents")
  public List<Student> getAllStudents() {
    return repository.readAllStudents();
  }


}
