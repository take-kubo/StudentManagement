package raisetech.StudentManagement.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
}