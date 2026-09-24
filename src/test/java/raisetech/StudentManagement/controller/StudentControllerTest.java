package raisetech.StudentManagement.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.service.StudentService;
import tools.jackson.databind.ObjectMapper;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

  @Autowired
  MockMvc mockMvc;

  @MockitoBean
  private StudentService service;

  private Validator validator;

  @BeforeEach
  void before() {
    validator = Validation.buildDefaultValidatorFactory().getValidator();
  }

  @Test
  void 受講生詳細の一覧検索が実行できてからのリストが返ってくること() throws Exception {
    mockMvc.perform(get("/students"))
        .andExpect(status().isOk());

    verify(service, times(1)).searchStudentList();
  }

  @Test
  void 受講生コース情報の一覧検索が実行できてからのリストが返ってくること() throws Exception {
    mockMvc.perform(get("/courses"))
        .andExpect(status().isOk());

    verify(service, times(1)).searchStudentsCourseList();
  }

  @Test
  void 受講生詳細の一件検索が実行できて受講生詳細が返ってくること() throws Exception {
    mockMvc.perform(get("/students/111111111111111111111111111111111111"))
        .andExpect(status().isOk());

    verify(service, times(1)).searchStudent("111111111111111111111111111111111111");
  }

  @Test
  void 受講生詳細の登録が実行できること() throws Exception {
    // 準備
    Student student = new Student();
    student.setId("111111111111111111111111111111111111");
    student.setName("テスト太郎");
    student.setFurigana("テストタロウ");
    student.setNickname("テスタ");
    student.setAge(40);
    student.setAddress("東京都");
    student.setEmail("testa@test.com");
    student.setGender("男");
    student.setRemark("特になし");
    student.setDeleted(false);

    StudentCourse studentCourseJava = new StudentCourse();
    studentCourseJava.setId("111111111111111111111111111111111111");
    studentCourseJava.setStudentId("111111111111111111111111111111111111");
    studentCourseJava.setCourseName("Javaコース");
    studentCourseJava.setCourseStartAt(LocalDateTime.of(2026, 9, 15, 19, 0, 0));
    studentCourseJava.setCourseEndAt(LocalDateTime.of(2026, 9, 15, 19, 0, 0));

    List<StudentCourse> studentCourseList = new ArrayList<>();
    studentCourseList.add(studentCourseJava);

    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(student);
    studentDetail.setStudentsCourses(studentCourseList);

    ObjectMapper objectMapper = new ObjectMapper();
    String json = objectMapper.writeValueAsString(studentDetail);

    mockMvc.perform(post("/students")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
        .andExpect(status().isCreated());

    verify(service, times(1)).registerStudent(studentDetail);
  }

  @Test
  void 受講生詳細の更新が実行できること() throws Exception {

    Student student = new Student();
    student.setId("111111111111111111111111111111111111");
    student.setName("テスト太郎");
    student.setFurigana("テストタロウ");
    student.setNickname("テスタ");
    student.setAge(40);
    student.setAddress("東京都");
    student.setEmail("testa@test.com");
    student.setGender("男");
    student.setRemark("特になし");
    student.setDeleted(false);

    StudentCourse studentCourseJava = new StudentCourse();
    studentCourseJava.setId("111111111111111111111111111111111111");
    studentCourseJava.setStudentId("111111111111111111111111111111111111");
    studentCourseJava.setCourseName("Javaコース");
    studentCourseJava.setCourseStartAt(LocalDateTime.of(2026, 9, 15, 19, 0, 0));
    studentCourseJava.setCourseEndAt(LocalDateTime.of(2026, 9, 15, 19, 0, 0));

    List<StudentCourse> studentCourseList = new ArrayList<>();
    studentCourseList.add(studentCourseJava);

    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(student);
    studentDetail.setStudentsCourses(studentCourseList);

    ObjectMapper objectMapper = new ObjectMapper();
    String json = objectMapper.writeValueAsString(studentDetail);

    mockMvc.perform(put("/students/111111111111111111111111111111111111")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
        .andExpect((status().isOk()));

    verify(service, times(1)).updateStudent("111111111111111111111111111111111111", studentDetail);

  }

  // 受講生に対するバリデーションのテスト
  @Test
  void 受講生に適切な値を設定した場合は入力チェックを通過すること() {
    // 準備
    Student student = new Student();
    student.setId("111111111111111111111111111111111111");
    student.setName("テスト太郎");
    student.setFurigana("テストタロウ");
    student.setNickname("テスタ");
    student.setAge(40);
    student.setAddress("東京都");
    student.setEmail("testa@test.com");
    student.setGender("男");
    student.setRemark("特になし");
    student.setDeleted(false);

    // 実行
    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    // 検証
    assertThat(violations.size()).isEqualTo(0);
  }

  @Test
  void 受講生の名前にnullを設定した場合は入力チェックに掛かること() {
    // 準備
    Student student = new Student();
    student.setId("111111111111111111111111111111111111");
    student.setName(null);  // 異常値
    student.setFurigana("テストタロウ");
    student.setNickname("テスタ");
    student.setAge(40);
    student.setAddress("東京都");
    student.setEmail("testa@test.com");
    student.setGender("男");
    student.setRemark("特になし");
    student.setDeleted(false);

    // 実行
    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    // 検証
    assertThat(violations.size()).isEqualTo(1);
  }

  @Test
  void 受講生の名前に２１文字以上の文字列をを設定した場合は入力チェックに掛かること() {
    // 準備
    Student student = new Student();
    student.setId("111111111111111111111111111111111111");
    student.setName("テストテストテストテストテストテストテスト"); // 異常値
    student.setFurigana("テストタロウ");
    student.setNickname("テスタ");
    student.setAge(40);
    student.setAddress("東京都");
    student.setEmail("testa@test.com");
    student.setGender("男");
    student.setRemark("特になし");
    student.setDeleted(false);

    // 実行
    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    // 検証
    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message").contains("名前は０文字以上２０文字以下です。");
  }

  @Test
  void 受講生のフリガナにnullを設定した場合は入力チェックに掛かること() {
    // 準備
    Student student = new Student();
    student.setId("111111111111111111111111111111111111");
    student.setName("テスト太郎");
    student.setFurigana(null);  // 異常値
    student.setNickname("テスタ");
    student.setAge(40);
    student.setAddress("東京都");
    student.setEmail("testa@test.com");
    student.setGender("男");
    student.setRemark("特になし");
    student.setDeleted(false);

    // 実行
    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    // 検証
    assertThat(violations.size()).isEqualTo(1);
  }

  @Test
  void 受講生のフリガナ３１文字以上の文字列を設定した場合は入力チェックに掛かること() {
    // 準備
    Student student = new Student();
    student.setId("111111111111111111111111111111111111");
    student.setName("テスト太郎");
    student.setFurigana(
        "テストテストテストテストテストテストテストテストテストテストテスト");  // 異常値
    student.setNickname("テスタ");
    student.setAge(40);
    student.setAddress("東京都");
    student.setEmail("testa@test.com");
    student.setGender("男");
    student.setRemark("特になし");
    student.setDeleted(false);

    // 実行
    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    // 検証
    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message").contains("フリガナは０文字以上３０文字以下です。");
  }

  @Test
  void 受講生のニックネームに２１文字以上の文字列を設定した場合は入力チェックに掛かること() {
    // 準備
    Student student = new Student();
    student.setId("111111111111111111111111111111111111");
    student.setName("テスト太郎");
    student.setFurigana("テストタロウ");
    student.setNickname("テストテストテストテストテストテストテスト"); // 異常値
    student.setAge(40);
    student.setAddress("東京都");
    student.setEmail("testa@test.com");
    student.setGender("男");
    student.setRemark("特になし");
    student.setDeleted(false);

    // 実行
    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    // 検証
    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message").contains("ニックネームは０文字以上２０文字以下です。");
  }

  @Test
  void 受講生のメールアドレスにnullを設定した場合は入力チェックに掛かること() {
    // 準備
    Student student = new Student();
    student.setId("111111111111111111111111111111111111");
    student.setName("テスト太郎");
    student.setFurigana("テストタロウ");
    student.setNickname("テスタ"); // 異常値
    student.setAge(40);
    student.setAddress("東京都");
    student.setEmail(null);
    student.setGender("男");
    student.setRemark("特になし");
    student.setDeleted(false);

    // 実行
    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    // 検証
    assertThat(violations.size()).isEqualTo(1);
  }

  @Test
  void 受講生のメールアドレスにメールアドレスの形式でない文字列をを設定した場合は入力チェックに掛かること() {
    // 準備
    Student student = new Student();
    student.setId("111111111111111111111111111111111111");
    student.setName("テスト太郎");
    student.setFurigana("テストタロウ");
    student.setNickname("テスタ"); // 異常値
    student.setAge(40);
    student.setAddress("東京都");
    student.setEmail("testtesttesttesttest");
    student.setGender("男");
    student.setRemark("特になし");
    student.setDeleted(false);

    // 実行
    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    // 検証
    assertThat(violations.size()).isEqualTo(1);
  }

  @Test
  void 受講生のメールアドレスに２０１文字以上の文字列を設定した場合は入力チェックに掛かること() {
    // 準備
    Student student = new Student();
    student.setId("111111111111111111111111111111111111");
    student.setName("テスト太郎");
    student.setFurigana("テストタロウ");
    student.setNickname("テスタ"); // 異常値
    student.setAge(40);
    student.setAddress("東京都");
    student.setEmail(
        "testtesttesttesttesttesttesttesttesttest"
            + "@"
            + "testtesttesttesttesttesttesttesttesttesttesttesttesttesttest"
            + "."
            + "testtesttesttesttesttesttesttesttesttesttesttesttesttesttest"
            + "."
            + "testtesttesttesttesttesttesttesttesttesttesttesttesttesttest"
    );
    student.setGender("男");
    student.setRemark("特になし");
    student.setDeleted(false);

    // 実行
    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    // 検証
    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message")
        .contains("メールアドレスは０文字以上２００文字以下です。");
  }

  @Test
  void 受講生の居住地に１０１文字以上の文字列を設定した場合は入力チェックに掛かること() {
// 準備
    Student student = new Student();
    student.setId("111111111111111111111111111111111111");
    student.setName("テスト太郎");
    student.setFurigana("テストタロウ");
    student.setNickname("テスタ"); // 異常値
    student.setAge(40);
    student.setAddress("テストテストテストテストテストテストテストテストテストテストテストテスト"
        + "テストテストテストテストテストテストテストテストテストテストテストテストテストテストテスト"
        + "テストテストテストテストテストテストテスト");
    student.setEmail("testa@test.com");
    student.setGender("男");
    student.setRemark("特になし");
    student.setDeleted(false);

    // 実行
    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    // 検証
    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message")
        .contains("居住地域は０文字以上１００文字以下です。");
  }

  @Test
  void 受講生の年齢に負の数値を設定した場合は入力チェックに掛かること() {
    // 準備
    Student student = new Student();
    student.setId("111111111111111111111111111111111111");
    student.setName("テスト太郎");
    student.setFurigana("テストタロウ");
    student.setNickname("テスタ"); // 異常値
    student.setAge(-20);
    student.setAddress("東京都");
    student.setEmail("testa@test.com");
    student.setGender("男");
    student.setRemark("特になし");
    student.setDeleted(false);

    // 実行
    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    // 検証
    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message").contains("年齢は０歳以上です。");
  }

  @Test
  void 受講生の年齢に２０１以上の数値を設定した場合は入力チェックに掛かること() {
    // 準備
    Student student = new Student();
    student.setId("111111111111111111111111111111111111");
    student.setName("テスト太郎");
    student.setFurigana("テストタロウ");
    student.setNickname("テスタ"); // 異常値
    student.setAge(201);
    student.setAddress("東京都");
    student.setEmail("testa@test.com");
    student.setGender("男");
    student.setRemark("特になし");
    student.setDeleted(false);

    // 実行
    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    // 検証
    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message").contains("年齢は２００歳以下です。");
  }

  @Test
  void 受講生の性別に１１文字以上の文字列を設定した場合は入力チェックに掛かること() {
    // 準備
    Student student = new Student();
    student.setId("111111111111111111111111111111111111");
    student.setName("テスト太郎");
    student.setFurigana("テストタロウ");
    student.setNickname("テスタ"); // 異常値
    student.setAge(40);
    student.setAddress("東京都");
    student.setEmail("testa@test.com");
    student.setGender("テストテストテストテスト");
    student.setRemark("特になし");
    student.setDeleted(false);

    // 実行
    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    // 検証
    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message").contains("性別は０文字以上１０文字以下です。");
  }

  @Test
  void 受講生の備考に５００１文字以上の文字列を設定した場合は入力チェックに掛かること() {
    // 準備
    Student student = new Student();
    student.setId("111111111111111111111111111111111111");
    student.setName("テスト太郎");
    student.setFurigana("テストタロウ");
    student.setNickname("テスタ"); // 異常値
    student.setAge(40);
    student.setAddress("東京都");
    student.setEmail("testa@test.com");
    student.setGender("男");

    String testString = "t".repeat(5001);
    student.setRemark(testString);

    student.setDeleted(false);

    // 実行
    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    // 検証
    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message").contains("備考は０文字以上５０００文字以下です。");
  }

  @Test
  void 受講生コース情報のコース名にnullを設定した場合入力チェックに掛かること() {
    // 準備
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setId("111111111111111111111111111111111111");
    studentCourse.setStudentId("111111111111111111111111111111111111");
    studentCourse.setCourseName(null);
    studentCourse.setCourseStartAt(LocalDateTime.of(2026, 9, 15, 19, 0, 0));
    studentCourse.setCourseEndAt(LocalDateTime.of(2026, 9, 15, 19, 0, 0));

    // 実行
    Set<ConstraintViolation<StudentCourse>> violations = validator.validate(studentCourse);

    // 検証
    assertThat(violations.size()).isEqualTo(1);
  }

  @Test
  void 受講生コース情報のコース名に５０文字以上の文字列を設定した場合入力チェックに掛かること() {
    // 準備
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setId("111111111111111111111111111111111111");
    studentCourse.setStudentId("111111111111111111111111111111111111");
    studentCourse.setCourseName(
        "テストテストテストテストテストテストテストテストテストテストテストテストテストテストテストテストテストテスト");
    studentCourse.setCourseStartAt(LocalDateTime.of(2026, 9, 15, 19, 0, 0));
    studentCourse.setCourseEndAt(LocalDateTime.of(2026, 9, 15, 19, 0, 0));

    // 実行
    Set<ConstraintViolation<StudentCourse>> violations = validator.validate(studentCourse);

    // 検証
    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message").contains("コース名は０文字以上５０文字以下です。");

  }

}