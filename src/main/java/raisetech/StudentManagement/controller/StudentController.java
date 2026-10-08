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
import org.springframework.web.bind.annotation.RequestParam;
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
   * 受講生詳細の名前による絞り込み検索です。
   *
   * @param name 受講生の名前
   * @return 受講生詳細のリスト
   */
  @Operation(summary = "名前による絞り込み検索検索", description = "受講生を名前で絞り込み検索します。")
  @GetMapping(value = "/students", params = "name")
  public List<StudentDetail> getStudentDetailListFilteredByName(
      @RequestParam(required = false) String name) {
    return service.searchStudentListByName(name);
  }

  /**
   * 受講生の名前のふりがなによる絞り込み検索です。
   *
   * @param furigana 受講生の名前のふりがな
   * @return 受講生詳細のリスト
   */
  @Operation(summary = "名前のふりがなによる絞り込み検索検索", description = "受講生を名前のふりがなで絞り込み検索します。")
  @GetMapping(value = "/students", params = "furigana")
  public List<StudentDetail> getStudentDetailListFilteredByFurigana(
      @RequestParam(required = false) String furigana) {
    return service.searchStudentListFilteredByFurigana(furigana);
  }

  /**
   * 受講生のニックネームによる絞り込み検索です。
   *
   * @param nickname 受講生のニックネーム
   * @return 受講生詳細のリスト
   */
  @Operation(summary = "ニックネームによる絞り込み検索検索", description = "受講生をニックネームで絞り込み検索します。")
  @GetMapping(value = "/students", params = "nickname")
  public List<StudentDetail> getStudentDetailListFilteredByNickname(
      @RequestParam(required = false) String nickname) {
    return service.searchStudentListFilteredByNickname(nickname);
  }

  /**
   * 受講生のメールアドレスによる絞り込み検索です。
   *
   * @param email 受講生のメールアドレス
   * @return 受講生詳細のリスト
   */
  @Operation(summary = "メールアドレスによる絞り込み検索検索", description = "受講生をメールアドレスで絞り込み検索します。")
  @GetMapping(value = "/students", params = "email")
  public List<StudentDetail> getStudentDetailListFilteredByEmail(
      @RequestParam(required = false) String email) {
    return service.searchStudentListFilteredByEmail(email);
  }

  /**
   * 受講生の居住地域による絞り込み検索です。
   *
   * @param address 受講生の居住地域
   * @return 受講生詳細のリスト
   */
  @Operation(summary = "居住地域による絞り込み検索検索", description = "受講生を居住地域で絞り込み検索します。")
  @GetMapping(value = "/students", params = "address")
  public List<StudentDetail> getStudentDetailListFilteredByAddress(
      @RequestParam(required = false) String address) {
    return service.searchStudentListFilteredByAddress(address);
  }

  /**
   * 受講生のジェンダーによる絞り込み検索です。
   *
   * @param gender 受講生のジェンダー
   * @return 受講生詳細のリスト
   */
  @Operation(summary = "ジェンダーによる絞り込み検索検索", description = "受講生をジェンダーで絞り込み検索します。")
  @GetMapping(value = "/students", params = "gender")
  public List<StudentDetail> getStudentDetailListFilteredByGender(
      @RequestParam(required = false) String gender) {
    return service.searchStudentListFilteredByGender(gender);
  }

  /**
   * 受講生の備考による絞り込み検索です。
   *
   * @param remark 受講生の備考
   * @return 受講生詳細のリスト
   */
  @Operation(summary = "備考による絞り込み検索検索", description = "受講生を備考で絞り込み検索します。")
  @GetMapping(value = "/students", params = "remark")
  public List<StudentDetail> getStudentDetailListFilteredByRemark(
      @RequestParam(required = false) String remark) {
    return service.searchStudentListFilteredByRemark(remark);
  }

  /**
   * 受講生の年齢による絞り込み検索です。
   *
   * @param minAge 受講生の年齢範囲の上限
   * @param maxAge 受講生の年齢範囲の下限
   * @return 受講生詳細のリスト
   */
  @Operation(summary = "年齢による絞り込み検索検索", description = "受講生を年齢で絞り込み検索します。")
  @GetMapping(value = "/students", params = {"minAge", "maxAge"})
  public List<StudentDetail> getStudentDetailListFilteredByRemark(
      @RequestParam(required = false) int minAge,
      @RequestParam(required = false) int maxAge) {
    return service.searchStudentListFilteredByAge(minAge, maxAge);
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

  /**
   * 受講生コース申込状況の更新を行います。
   *
   * @param id 受講生コース申込状況ID
   * @param status 受講生コース申込状況
   * @return 実行結果
   */
  @Operation(summary = "受講生コース申込状況更新", description = "受講生コース申込状況を更新します。")
  @PutMapping("/registrationStatuses/{id}")
  public ResponseEntity<SuccessDTO> updateStudentCourseRegistrationStatus(
      @PathVariable("id") String id, @RequestBody @Valid StudentCourseRegistrationStatus status) {

    service.updateStudentCourseRegistrationStatus(id, status);

    SuccessDTO successDTO = new SuccessDTO("Update success.", status.getId());
    return ResponseEntity.status(HttpStatus.OK).body(successDTO);

  }
}
