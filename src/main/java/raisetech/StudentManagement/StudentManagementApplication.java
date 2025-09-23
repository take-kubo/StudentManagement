package raisetech.StudentManagement;

import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class StudentManagementApplication {

  private String name = "Yamada Taro";
  private String age = "40";

  //
  @Autowired
  private StudentService studentService;

  public static void main(String[] args) {
    SpringApplication.run(StudentManagementApplication.class, args);
  }

  /* 課題：講座の再現をする */
  @GetMapping("/studentInfo")
  public String getStudentInfo() {
    return name + " " + age + "歳";
  }

  @PostMapping("/studentInfo")
  public void setStudentInfo(String name, String age) {
    this.name = name;
    this.age = age;
  }

  @PostMapping("/studentName")
  public void updateStudentName(String name) {
    this.name = name;
  }


  /* 課題：より複雑なものにチャレンジする（Mapを使うなど） */
  @GetMapping("/studentInfo2")
  public String getStudentInfo2() {
    return studentService.studentMap.entrySet().stream()
        .sorted(Map.Entry.comparingByKey(Comparator.nullsLast(new MixedCharacterComparator())))
        .map(s -> s.getKey() + " is " + s.getValue() + " years old.")
        .collect(Collectors.joining("\n"));
  }

  @PostMapping("/studentInfo2")
  public void setStudentInfo2(
      @RequestParam(required = true, defaultValue = "unknown") String studentName,
      @RequestParam(required = true, defaultValue = "unknown") String studentAge) {

    studentService.studentMap.put(studentName, studentAge);

  }
}
