package raisetech.StudentManagement;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
  public ResponseEntity<?> setStudentInfo(
      @Valid @RequestBody StudentPersonalDataDTO studentPersonalDataDTO,
      BindingResult bindingResult) {

    // 入力チェック失敗。HTTPステータスコード400とエラーメッセージをクライアントに返す
    if (bindingResult.hasErrors()) {
      return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
    }

    // 入力チェック成功。受講生情報を変数に代入し、HTTPステータスコード200とメッセージをクライアントに返す
    studentService.setName(studentPersonalDataDTO.getStudentName());
    studentService.setAge(studentPersonalDataDTO.getStudentAge());

    return ResponseEntity.status(HttpStatus.OK).body("受講生情報を変数に代入しました");

  }

  @PostMapping("/studentName")
  public ResponseEntity<?> updateStudentName(
      @Valid @RequestBody StudentPersonalDataDTO studentPersonalDataDTO,
      BindingResult bindingResult) {

    // 入力チェック失敗。HTTPステータスコード400とエラーメッセージをクライアントに返す
    if (bindingResult.hasErrors()) {
      return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
    }

    // 入力チェック成功。受講生の名前を変数に代入し、HTTPステータスコード200とメッセージをクライアントに返す
    studentService.setName(studentPersonalDataDTO.getStudentName());

    return ResponseEntity.status(HttpStatus.OK).body("受講生の名前を変数に代入しました");

  }


  /* 課題：より複雑なものにチャレンジする（Mapを使うなど） */
  @GetMapping("/studentInfo2")
  public String getStudentInfo2() {
    return studentService.getAllStudentsPersonalData();
  }

  @PostMapping("/studentInfo2")
  public ResponseEntity<?> setStudentInfo2(
      @Valid @RequestBody StudentPersonalDataDTO studentPersonalDataDTO,  // JSONで受信し入力チェック
      BindingResult bindingResult) {  // 入力チェックの結果を保持するオブジェクト

    // 入力チェック失敗。HTTPステータスコード400とエラーメッセージをクライアントに返す
    if (bindingResult.hasErrors()) {
      return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
    }

    // 入力チェック成功。受講生情報をマップに登録し、HTTPステータスコード201とメッセージをクライアントに返す
    studentService.setStudentPersonalDataMap(
        studentPersonalDataDTO.getStudentName(), studentPersonalDataDTO.getStudentAge());

    return ResponseEntity.status(HttpStatus.CREATED).body("受講生情報をマップに登録しました");

  }

}
