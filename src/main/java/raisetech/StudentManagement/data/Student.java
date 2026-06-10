package raisetech.StudentManagement.data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Student {

  private String id;

  @NotNull
  @Size(max = 20, message = "名前は０文字以上２０文字以下です。")
  private String name;

  @NotNull
  @Size(max = 30, message = "フリガナは０文字以上３０文字以下です。")
  private String furigana;

  @Size(max = 20, message = "ニックネームは０文字以上２０文字以下です。")
  private String nickname;

  @NotNull
  @Email
  @Size(max = 200, message = "メールアドレスは０文字以上２００文字以下です。")
  private String email;

  @Size(max = 100, message = "居住地域は０文字以上１００文字以下です。")
  private String address;

  @Min(value = 0, message = "年齢は０歳以上です。")
  @Max(value = 200, message = "年齢は２００歳以下です。")
  private int age;

  @Size(max = 10, message = "性別は０文字以上１０文字以下です。")
  private String gender;

  @Size(max = 5000, message = "備考は０文字以上５０００文字以下です。")
  private String remark;

  private boolean isDeleted;

}
