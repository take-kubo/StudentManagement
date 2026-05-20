package raisetech.StudentManagement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
  public Object getStudent(@RequestParam String name) {
    Student student = repository.searchByName(name);

    if (Objects.isNull(student)) {
      Map<String, Object> errorMessageMap = new HashMap<>();

      errorMessageMap.put("title", "Not Found");
      errorMessageMap.put("status", 404);
      errorMessageMap.put("detail", "指定された名前の受講生が見つかりませんでした。");

      return errorMessageMap;
    }

    return student.getName() + " " + student.getAge() + "歳";

  }

  @PostMapping("/student")
  public void registerStudent(String name, int age) {
    repository.registerStudent(name, age);
  }

  @PatchMapping("/student")
  public void updateStudent(String name, int age) {
    repository.updateStudent(name, age);
  }

  @DeleteMapping("/student")
  public void deleteStudent(String name) {
    repository.deleteStudent(name);
  }

  @GetMapping("/allStudents")
  public List<Student> getAllStudents() {
    return repository.readAllStudents();
  }


}
