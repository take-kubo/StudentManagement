package raisetech.StudentManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class StudentManagementApplication {

 // @Autowired アノテーションは studentService と StudentService クラスを自動で関連づけてくれます
  @Autowired
  private StudentService studentService;

  public static void main(String[] args) {
    SpringApplication.run(StudentManagementApplication.class, args);
  }


  /* 課題：講座の再現をする */
  @GetMapping("/studentInfo")
  public String getStudentInfo() {
    return studentService.getName() + " " + studentService.getAge() + "歳";
  }

  @PostMapping("/studentInfo")
  public void setStudentInfo(@RequestBody StudentPersonalDataDTO studentPersonalDataDTO) {
    studentService.setName(studentPersonalDataDTO.getStudentName());
    studentService.setAge(studentPersonalDataDTO.getStudentAge());
  }

  @PostMapping("/studentName")
  public void updateStudentName(String name) {
    studentService.setName(name);
  }


  /* 課題：より複雑なものにチャレンジする（Mapを使うなど） */
  @GetMapping("/studentInfo2")
  public String getStudentInfo2() {
    return studentService.getAllStudentsPersonalData();
  }

  @PostMapping("/studentInfo2")
  public void setStudentInfo2(@RequestBody StudentPersonalDataDTO studentPersonalDataDTO) {
    studentService.setStudentPersonalDataMap(studentPersonalDataDTO.getStudentName(), studentPersonalDataDTO.getStudentAge());
  }

}
