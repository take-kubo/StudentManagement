package raisetech.StudentManagement.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;

@MybatisTest
class StudentRepositoryTest {

  @Autowired
  private StudentRepository sut;

  @Test
  void 受講生の全件検索が行えること() {
    List<Student> actual = sut.searchStudentList();
    assertThat(actual.size()).isEqualTo(10);
  }

  @Test
  void 受講生コース情報の全件検索が行えること() {
    List<StudentCourse> actual = sut.searchStudentCourseList();
    assertThat(actual.size()).isEqualTo(10);
  }

  @Test
  void 受講生の１件検索が行えること() {
    Student student = new Student();
    student.setId("1");
    student.setName("佐藤 太郎");
    student.setFurigana("さとう たろう");
    student.setNickname("たろちゃん");
    student.setAge(20);
    student.setAddress("東京都新宿区西新宿1-1-1");
    student.setEmail("taro.sato@example.com");
    student.setGender("男性");
    student.setRemark("野球部所属");
    student.setDeleted(false);

    Student expected = student;
    Student actual = sut.searchStudent("1");

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void 受講生コース情報の受講生IDによる検索が行えること() {
    List<StudentCourse> actual = sut.searchStudentCourseListById("1");
    assertThat(actual.size()).isEqualTo(2);
  }

  @Test
  void 受講生の登録が行えること() {
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

    sut.registerStudent(student);

    List<Student> actual = sut.searchStudentList();

    assertThat(actual.size()).isEqualTo(11);
  }

  @Test
  void 受講生コース情報登録が行えること() {
    StudentCourse studentCourseJava = new StudentCourse();
    studentCourseJava.setId("111111111111111111111111111111111111");
    studentCourseJava.setStudentId("111111111111111111111111111111111111");
    studentCourseJava.setCourseName("Javaコース");
    studentCourseJava.setCourseStartAt(LocalDateTime.of(2026, 9, 15, 19, 0, 0));
    studentCourseJava.setCourseEndAt(LocalDateTime.of(2026, 9, 15, 19, 0, 0));

    sut.registerStudentCourse(studentCourseJava);

    List<StudentCourse> actual = sut.searchStudentCourseList();

    assertThat(actual.size()).isEqualTo(11);

  }

  @Test
  void 受講生の更新が行えること() {
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

    sut.updateStudent(student);
    Student actual = sut.searchStudent("1");
    Student expected = student;

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void 受講生コース情報の更新が行えること() {
    StudentCourse studentCourseJava = new StudentCourse();
    studentCourseJava.setId("1");
    studentCourseJava.setStudentId("1");
    studentCourseJava.setCourseName("Java基礎コース");
    studentCourseJava.setCourseStartAt(LocalDateTime.of(2026, 4, 1, 0, 0, 0));
    studentCourseJava.setCourseEndAt(LocalDateTime.of(2026, 6, 30, 23, 59, 59));

    sut.updateStudentCourse(studentCourseJava);
    StudentCourse actual = sut.searchStudentCourse("1");
    StudentCourse expected = studentCourseJava;

    assertThat(actual).isEqualTo(expected);
  }
}