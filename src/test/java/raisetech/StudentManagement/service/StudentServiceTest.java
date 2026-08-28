package raisetech.StudentManagement.service;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

  @Mock
  private StudentRepository repository;

  @Mock
  private StudentConverter converter;

  private StudentService sut;

  @BeforeEach
  void before() {
    sut = new StudentService(repository, converter);

  }

  @Test
  void 受講生詳細の一覧検索_リポジトリとコンバーターの処理が適切に呼び出せていること() {
    List<Student> studentList = new ArrayList<>();
    List<StudentCourse> studentCourseList = new ArrayList<>();
    Mockito.when(repository.searchStudentList()).thenReturn(studentList);
    Mockito.when(repository.searchStudentCourseList()).thenReturn(studentCourseList);

    sut.searchStudentList();

    Mockito.verify(repository, Mockito.times(1)).searchStudentList();
    Mockito.verify(repository, Mockito.times(1)).searchStudentCourseList();
    Mockito.verify(converter, Mockito.times(1))
        .convertStudentDetails(studentList, studentCourseList);
  }

}