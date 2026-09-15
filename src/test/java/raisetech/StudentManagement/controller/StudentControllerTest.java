package raisetech.StudentManagement.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import raisetech.StudentManagement.service.StudentService;

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
}