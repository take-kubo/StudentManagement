package raisetech.StudentManagement.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.data.SuccessDTO;
import raisetech.StudentManagement.domain.StudentCourseRegistrationStatus;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.exception.TestException;
import raisetech.StudentManagement.service.StudentService;

/**
 * 受講生の検索や登録、更新などを行うRest APIとして受け付けるControllerです。
 */
@Validated
@RestController
public class StudentController {

  private final StudentService service;

  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }

  /**
   * 受講生詳細の検索です。
   * IDに紐づく任意の受講生の情報を取得します。
   *
   * @param id 受講生ID
   * @return 受講生詳細
   */
  @Operation(summary = "受講生検索", description = "IDによって受講生を検索します。")
  @GetMapping("/students/{id}")
  public StudentDetail getStudent(@PathVariable @Size(min = 36, max = 36) String id) {
    return service.searchStudent(id);
  }

  /**
   * 受講生詳細の一覧検索です。
   * 全件検索を行うので、条件指定は行いません。
   *
   * @return 受講生詳細一覧（全件）
   */
  @Operation(summary = "一覧検索", description = "受講生の一覧を検索します。")
  @GetMapping("/students")
  public List<StudentDetail> getStudentList() {
    return service.searchStudentList();
  }

  /**
   * 受講生コース情報の一覧検索です。
   * 全件検索を行うので、条件指定は行いません。
   *
   * @return 受講生コース情報一覧（全件）
   */
  @Operation(summary = "受講生コース情報一覧検索", description = "受講生コース情報の一覧を検索します。")
  @GetMapping("/courses")
  public List<StudentCourse> getStudentsCourseList() {
    return service.searchStudentsCourseList();
  }

  /**
   * 受講生詳細の登録を行います。
   *
   * @param studentDetail 受講生詳細
   * @return 実行結果
   */
  @Operation(summary = "受講生登録", description = "受講生を登録します。")
  @PostMapping("/students")
  public ResponseEntity<SuccessDTO> registerStudent(
      @RequestBody @Valid StudentDetail studentDetail) {

    service.registerStudent(studentDetail);

    SuccessDTO successDTO = new SuccessDTO("Register success.", studentDetail.getStudent().getId());

    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(studentDetail.getStudent().getId()).toUri();

    return ResponseEntity.status(HttpStatus.CREATED)
        .location(uri)
        .body(successDTO);

  }

  /**
   *受講生詳細の更新を行います。キャンセルフラグの更新もここで行います（論理削除）
   *
   * @param id 受講生ID
   * @param studentDetail 受講生詳細
   * @return 実行結果
   */
  @Operation(summary = "受講生更新", description = "受講生詳細を更新します。")
  @PutMapping("/students/{id}")
  public ResponseEntity<SuccessDTO> updateStudent(
      @PathVariable("id") String id, @RequestBody @Valid StudentDetail studentDetail) {

    service.updateStudent(id, studentDetail);

    SuccessDTO successDTO = new SuccessDTO("Update success.", studentDetail.getStudent().getId());
    return ResponseEntity.status(HttpStatus.OK).body(successDTO);

  }

  @Operation(summary = "利用不可", description = "このAPIは利用できません。アクセスするとカスタム例外を投げます。")
  @GetMapping("/testException")
  public void getTestException() throws TestException {
    throw new TestException(
        "現在このAPIは利用できません。URLは「studentList」ではなく「students」を利用してください。");
  }

  /**
   * 受講生コース申込状況の一覧検索です。
   * 全件検索を行うので、条件指定は行いません。
   *
   * @return 受講生コーズ申込状況一覧（全件）
   */
  @Operation(summary = "受講生コース申込状況一覧検索", description = "受講生コース申込状況の一覧検索をします。")
  @GetMapping("/registrationStatuses")
  List<StudentCourseRegistrationStatus> getStudentCourseRegistrationStatusList() {
    return service.searchStudentCourseRegistrationStatusList();
  }

  /**
   * 受講生コース申込状況の一件検索です。
   * IDに紐づく任意の受講生コース申込状況を取得します。
   *
   * @param id 受講生コース申込状況ID
   * @return 受講生コース申込状況
   */
  @Operation(summary = "受講生コース申込状況一件検索", description = "IDによって受講生コース申込状況を検索します。")
  @GetMapping("/registrationStatuses/{id}")
  public StudentCourseRegistrationStatus getStudentCourseRegistrationStatus(
      @PathVariable @Size(min = 36, max = 36) String id) {
    return service.searchStudentCourseRegistrationStatusById(id);
  }

  /**
   * 受講生コース申込状況の登録です。
   *
   * @param studentCourseRegistrationStatus　受講生コース申込状況
   * @return 実行結果
   */
  @Operation(summary = "受講生コース申込状況登録", description = "受講生コース申込状況を登録します。")
  @PostMapping("/registrationStatuses")
  public ResponseEntity<SuccessDTO> registerStudentCourseRegistrationStatus(
      @RequestBody @Valid StudentCourseRegistrationStatus studentCourseRegistrationStatus) {

    service.registerStudentCourseRegistrationStatus(studentCourseRegistrationStatus);

    SuccessDTO successDTO = new SuccessDTO("Register success.",
        studentCourseRegistrationStatus.getId());

    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(studentCourseRegistrationStatus.getId()).toUri();

    return ResponseEntity.status(HttpStatus.CREATED)
        .location(uri)
        .body(successDTO);

  }
}
