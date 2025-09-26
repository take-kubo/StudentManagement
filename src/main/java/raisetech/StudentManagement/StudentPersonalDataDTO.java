package raisetech.StudentManagement;

import jakarta.validation.constraints.NotBlank;

/*
  リクエストのBodyで渡された JSON データを受け取るための DTO(Data Transfer Object) クラス

  コントローラとサービスの間で受講生情報をやり取りするために使用します。
 */
public class StudentPersonalDataDTO {

  // 受講生の名前
  @NotBlank
  private String studentName;

  // 受講生の年齢
  @NotBlank
  private String studentAge;

  // コンストラクタ
  public StudentPersonalDataDTO() {
    studentName = "unknown";
    studentAge = "unknown";
  }

  /* getter */
  public String getStudentName() {
    return studentName;
  }

  public String getStudentAge() {
    return studentAge;
  }

  /* setter */
  public void setStudentName(String studentName) {
    this.studentName = studentName;
  }

  public void setStudentAge(String studentAge) {
    this.studentAge = studentAge;
  }

}
