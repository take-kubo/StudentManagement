package raisetech.StudentManagement.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講生コースの申込状況")
@Getter
@Setter
@EqualsAndHashCode
public class StudentCourseRegistrationStatus {

  private String id = "";

  private String studentCourseId = "";

  @Pattern(regexp = "仮登録|本登録|受講中|受講終了")
  private String status = "";

}
