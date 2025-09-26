package raisetech.StudentManagement;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/*
  リクエストのBodyで渡された JSON データを受け取るための DTO(Data Transfer Object) クラス

  コントローラとサービスの間で受講生情報をやり取りするために使用します。
 */
public class StudentPersonalDataDTO {

  // 受講生の名前
  @NotBlank
  private String studentName;

  // 受講生の年齢
  @NotNull
  @Max(200)
  @Min(0)
  private Integer studentAge;

  // コンストラクタ
  public StudentPersonalDataDTO() {
    studentName = "unknown";
    studentAge = 0;
  }

  /* getter */
  public String getStudentName() {
    return studentName;
  }

  public Integer getStudentAge() {
    return studentAge;
  }

  /* setter */
  public void setStudentName(String studentName) {
    this.studentName = studentName;
  }

  public void setStudentAge(Integer studentAge) {
    this.studentAge = studentAge;
  }

}
