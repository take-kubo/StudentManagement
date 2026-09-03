package raisetech.StudentManagement.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.exception.StudentNotFoundException;
import raisetech.StudentManagement.repository.StudentRepository;
import raisetech.StudentManagement.service.converter.StudentConverter;

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
    // 事前準備
    Student student1 = new Student();
    student1.setId("1");
    student1.setName("テスト太郎");
    student1.setFurigana("テストタロウ");
    student1.setNickname("テスタ");
    student1.setAge(40);
    student1.setAddress("東京都");
    student1.setEmail("testa@test.com");
    student1.setGender("男");
    student1.setRemark("特になし");
    student1.setDeleted(false);

    Student student2 = new Student();
    student2.setId("2");
    student2.setName("テスト次郎");
    student2.setFurigana("テストジロウ");
    student2.setNickname("テスジ");
    student2.setAge(40);
    student2.setAddress("神奈川県");
    student2.setEmail("tesji@test.com");
    student2.setGender("男");
    student2.setRemark("特になし");
    student2.setDeleted(false);

    List<Student> studentList = new ArrayList<>();
    studentList.add(student1);
    studentList.add(student2);
    Mockito.when(repository.searchStudentList()).thenReturn(studentList);

    StudentCourse studentCourseJava = new StudentCourse();
    studentCourseJava.setId("1");
    studentCourseJava.setStudentId("1");
    studentCourseJava.setCourseName("Javaコース");
    studentCourseJava.setCourseStartAt(LocalDateTime.now());
    studentCourseJava.setCourseEndAt(LocalDateTime.now());

    StudentCourse studentCourseAWS = new StudentCourse();
    studentCourseAWS.setId("2");
    studentCourseAWS.setStudentId("2");
    studentCourseAWS.setCourseName("AWSコース");
    studentCourseAWS.setCourseStartAt(LocalDateTime.now());
    studentCourseAWS.setCourseEndAt(LocalDateTime.now());

    List<StudentCourse> studentCourseList = new ArrayList<>();
    studentCourseList.add(studentCourseJava);
    studentCourseList.add(studentCourseAWS);
    Mockito.when(repository.searchStudentCourseList()).thenReturn(studentCourseList);

    StudentDetail studentDetail1 = new StudentDetail();
    studentDetail1.setStudent(student1);
    List<StudentCourse> studentCourseList1 = new ArrayList<>();
    studentCourseList1.add(studentCourseJava);
    studentDetail1.setStudentsCourses(studentCourseList1);

    StudentDetail studentDetail2 = new StudentDetail();
    studentDetail2.setStudent(student2);
    List<StudentCourse> studentCourseList2 = new ArrayList<>();
    studentCourseList2.add(studentCourseAWS);
    studentDetail2.setStudentsCourses(studentCourseList2);

    List<StudentDetail> studentDetailList = new ArrayList<>();
    studentDetailList.add(studentDetail1);
    studentDetailList.add(studentDetail2);

    // 実行
    List<StudentDetail> expected = studentDetailList;
    List<StudentDetail> actual = sut.searchStudentList();
    Assertions.assertEquals(expected, actual);

    // 検証
    Mockito.verify(repository, Mockito.times(1)).searchStudentList();
    Mockito.verify(repository, Mockito.times(1)).searchStudentCourseList();
    Mockito.verify(converter, Mockito.times(1))
        .convertStudentDetails(studentList, studentCourseList);
  }

  @Test
  void 受講生コース情報の一覧検索_リポジトリの処理が適切に呼び出せていること() {
    List<StudentCourse> studentCourseList = new ArrayList<>();
    Mockito.when(repository.searchStudentCourseList()).thenReturn(studentCourseList);

    sut.searchStudentsCourseList();

    Mockito.verify(repository, Mockito.times(1)).searchStudentCourseList();
  }

  @Test
  void 受講生の一件検索_リポジトリの処理が適切に呼び出せていること() {
    // 事前準備
    Student student = new Student();
    student.setId("1");
    student.setName("テスト太郎");
    student.setFurigana("テストタロウ");
    student.setNickname("テスタ");
    student.setAge(40);
    student.setAddress("東京都");
    student.setEmail("testa@test.com");
    student.setGender("男");
    student.setRemark("特になし");
    student.setDeleted(false);
    Mockito.when(repository.searchStudent("1")).thenReturn(student);

    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setId("1");
    studentCourse.setCourseName("Javaコース");
    studentCourse.setStudentId("1");
    studentCourse.setCourseStartAt(LocalDateTime.now());
    studentCourse.setCourseEndAt(LocalDateTime.now());
    List<StudentCourse> studentCourseList = new ArrayList<>(List.of(studentCourse));
    Mockito.when(repository.searchStudentCourseListById("1")).thenReturn(studentCourseList);

    // 実行
    sut.searchStudent("1");

    // 検証
    Mockito.verify(repository, Mockito.times(1)).searchStudent("1");
    Mockito.verify(repository, Mockito.times(1)).searchStudentCourseListById("1");
  }

  @Test
  void 受講生の１件検索_存在しないIDで検索をしたとき適切に例外を投げること() {
    // 事前準備
    Mockito.when(repository.searchStudent("111111111111111111111111111111111111")).thenReturn(null);

    // 検証
    Assertions.assertThrows(StudentNotFoundException.class,
        () -> sut.searchStudent("111111111111111111111111111111111111"));
  }

  @Test
  void 受講生の登録_リポジトリの処理が適切に呼び出せていること() {
    // 準備
    Student student = new Student();
    student.setId("111111111111111111111111111111111111");
    student.setName("山田太郎");
    student.setFurigana("ヤマダタロウ");
    student.setNickname("タロウ");
    student.setAge(36);
    student.setAddress("東京都");
    student.setEmail("taro@test.com");
    student.setGender("男");
    student.setRemark("特になし");
    student.setDeleted(false);

    StudentCourse studentCourseJava = new StudentCourse();
    studentCourseJava.setId("111111111111111111111111111111111111");
    studentCourseJava.setStudentId("111111111111111111111111111111111111");
    studentCourseJava.setCourseName("Javaコース");
    studentCourseJava.setCourseStartAt(LocalDateTime.now());
    studentCourseJava.setCourseEndAt(LocalDateTime.now());

    StudentCourse studentCourseAWS = new StudentCourse();
    studentCourseAWS.setId("111111111111111111111111111111111111");
    studentCourseAWS.setStudentId("111111111111111111111111111111111111");
    studentCourseAWS.setCourseName("AWSコース");
    studentCourseAWS.setCourseStartAt(LocalDateTime.now());
    studentCourseAWS.setCourseEndAt(LocalDateTime.now());

    List<StudentCourse> studentCourseList = List.of(studentCourseJava, studentCourseAWS);

    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(student);
    studentDetail.setStudentsCourses(studentCourseList);

    // 実行
    sut.registerStudent(studentDetail);

    // 検証
    Mockito.verify(repository, Mockito.times(1)).registerStudent(student);
    Mockito.verify(repository, Mockito.times(1)).registerStudentCourse(studentCourseJava);
    Mockito.verify(repository, Mockito.times(1)).registerStudentCourse(studentCourseAWS);
  }

  @Test
  void 受講生の更新_リポジトリの処理が適切に呼び出せていること() {
    // 準備
    Student studentInDB = new Student();
    studentInDB.setId("791ea084-7d68-11f1-be12-84a93e79e4f1");
    studentInDB.setName("鈴木一郎");
    studentInDB.setFurigana("スズキイチロウ");
    studentInDB.setNickname("イチ");
    studentInDB.setAge(20);
    studentInDB.setAddress("長野県");
    studentInDB.setEmail("ichi@test.com");
    studentInDB.setGender("男");
    studentInDB.setRemark("特になし");
    studentInDB.setDeleted(false);

    Student student = new Student();
    student.setId("791ea084-7d68-11f1-be12-84a93e79e4f1");
    student.setName("山田太郎");
    student.setFurigana("ヤマダタロウ");
    student.setNickname("タロウ");
    student.setAge(36);
    student.setAddress("東京都");
    student.setEmail("taro@test.com");
    student.setGender("男");
    student.setRemark("特になし");
    student.setDeleted(false);

    StudentCourse studentCourseJava = new StudentCourse();
    studentCourseJava.setStudentId("791ea084-7d68-11f1-be12-84a93e79e4f1");
    studentCourseJava.setCourseName("Javaコース");
    studentCourseJava.setCourseStartAt(LocalDateTime.now());
    studentCourseJava.setCourseEndAt(LocalDateTime.now());

    StudentCourse studentCourseAWS = new StudentCourse();
    studentCourseAWS.setStudentId("791ea084-7d68-11f1-be12-84a93e79e4f1");
    studentCourseAWS.setCourseName("AWSコース");
    studentCourseAWS.setCourseStartAt(LocalDateTime.now());
    studentCourseAWS.setCourseEndAt(LocalDateTime.now());

    List<StudentCourse> studentCourseList = List.of(studentCourseJava, studentCourseAWS);

    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(student);
    studentDetail.setStudentsCourses(studentCourseList);

    Mockito.when(repository.searchStudent("791ea084-7d68-11f1-be12-84a93e79e4f1"))
        .thenReturn(studentInDB);

    // 実行
    sut.updateStudent("791ea084-7d68-11f1-be12-84a93e79e4f1", studentDetail);

    // 検証
    Mockito.verify(repository, Mockito.times(1))
        .searchStudent("791ea084-7d68-11f1-be12-84a93e79e4f1");
    Mockito.verify(repository, Mockito.times(1)).updateStudent(student);
    Mockito.verify(repository, Mockito.times(1)).updateStudentCourse(studentCourseJava);
    Mockito.verify(repository, Mockito.times(1)).updateStudentCourse(studentCourseAWS);

  }

  @Test
  void 受講生の更新_存在しないIDで検索をしたとき適切に例外を投げること() {
    // 事前準備
    Student student = new Student();
    student.setId("791ea084-7d68-11f1-be12-84a93e79e4f1");
    student.setName("山田太郎");
    student.setFurigana("ヤマダタロウ");
    student.setNickname("タロウ");
    student.setAge(36);
    student.setAddress("東京都");
    student.setEmail("taro@test.com");
    student.setGender("男");
    student.setRemark("特になし");
    student.setDeleted(false);

    StudentCourse studentCourseJava = new StudentCourse();
    studentCourseJava.setStudentId("791ea084-7d68-11f1-be12-84a93e79e4f1");
    studentCourseJava.setCourseName("Javaコース");
    studentCourseJava.setCourseStartAt(LocalDateTime.now());
    studentCourseJava.setCourseEndAt(LocalDateTime.now());

    StudentCourse studentCourseAWS = new StudentCourse();
    studentCourseAWS.setStudentId("791ea084-7d68-11f1-be12-84a93e79e4f1");
    studentCourseAWS.setCourseName("AWSコース");
    studentCourseAWS.setCourseStartAt(LocalDateTime.now());
    studentCourseAWS.setCourseEndAt(LocalDateTime.now());

    List<StudentCourse> studentCourseList = List.of(studentCourseJava, studentCourseAWS);

    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(student);
    studentDetail.setStudentsCourses(studentCourseList);

    Mockito.when(repository.searchStudent("791ea084-7d68-11f1-be12-84a93e79e4f1")).thenReturn(null);

    // 検証
    Assertions.assertThrows(StudentNotFoundException.class,
        () -> sut.updateStudent("791ea084-7d68-11f1-be12-84a93e79e4f1", studentDetail));
  }
}