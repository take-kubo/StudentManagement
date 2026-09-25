package raisetech.StudentManagement.service.converter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;

public class StudentConverterTest {

  private StudentConverter sut;

  @BeforeEach
  void before() {
    sut = new StudentConverter();
  }

  @Test
  void 受講生のリストと受講生コース情報のリストから受講生詳細のリストが適切に生成されること() {
    // 事前準備
    //　受講生のリストを作成
    Student student1 = new Student();
    student1.setId("111111111111111111111111111111111111");
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
    student2.setId("222222222222222222222222222222222222");
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

    // 受講生コース情報のリストを作成
    StudentCourse studentCourseJava = new StudentCourse();
    studentCourseJava.setId("111111111111111111111111111111111111");
    studentCourseJava.setStudentId("111111111111111111111111111111111111");
    studentCourseJava.setCourseName("Javaコース");
    studentCourseJava.setCourseStartAt(LocalDateTime.now());
    studentCourseJava.setCourseEndAt(LocalDateTime.now());

    StudentCourse studentCourseAWS = new StudentCourse();
    studentCourseAWS.setId("222222222222222222222222222222222222");
    studentCourseAWS.setStudentId("111111111111111111111111111111111111");
    studentCourseAWS.setCourseName("AWSコース");
    studentCourseAWS.setCourseStartAt(LocalDateTime.now());
    studentCourseAWS.setCourseEndAt(LocalDateTime.now());

    List<StudentCourse> studentCourseList = new ArrayList<>();
    studentCourseList.add(studentCourseJava);
    studentCourseList.add(studentCourseAWS);

    // 受講生詳細のリストを作成
    StudentDetail studentDetail1 = new StudentDetail();
    studentDetail1.setStudent(student1);
    List<StudentCourse> studentCourseList1 = new ArrayList<>();
    studentCourseList1.add(studentCourseJava);
    studentCourseList1.add(studentCourseAWS);
    studentDetail1.setStudentsCourses(studentCourseList1);

    StudentDetail studentDetail2 = new StudentDetail();
    studentDetail2.setStudent(student2);
    List<StudentCourse> studentCourseList2 = new ArrayList<>();
    studentDetail2.setStudentsCourses(studentCourseList2);

    List<StudentDetail> studentDetailList = new ArrayList<>();
    studentDetailList.add(studentDetail1);
    studentDetailList.add(studentDetail2);

    // 実行
    List<StudentDetail> expected = studentDetailList;
    List<StudentDetail> actual = sut.convertStudentDetails(studentList, studentCourseList);

    // 検証
    Assertions.assertEquals(expected, actual);
  }

  @Test
  void 受講生のIDがnullのときNPEを投げること() {
    // 事前準備
    //　受講生のリストを作成
    Student student1 = new Student();
    student1.setId(null);
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
    student2.setId("222222222222222222222222222222222222");
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

    // 受講生コース情報のリストを作成
    StudentCourse studentCourseJava = new StudentCourse();
    studentCourseJava.setId("111111111111111111111111111111111111");
    studentCourseJava.setStudentId("111111111111111111111111111111111111");
    studentCourseJava.setCourseName("Javaコース");
    studentCourseJava.setCourseStartAt(LocalDateTime.now());
    studentCourseJava.setCourseEndAt(LocalDateTime.now());

    StudentCourse studentCourseAWS = new StudentCourse();
    studentCourseAWS.setId("222222222222222222222222222222222222");
    studentCourseAWS.setStudentId("111111111111111111111111111111111111");
    studentCourseAWS.setCourseName("AWSコース");
    studentCourseAWS.setCourseStartAt(LocalDateTime.now());
    studentCourseAWS.setCourseEndAt(LocalDateTime.now());

    List<StudentCourse> studentCourseList = new ArrayList<>();
    studentCourseList.add(studentCourseJava);
    studentCourseList.add(studentCourseAWS);

    // 検証
    Assertions.assertThrows(NullPointerException.class,
        () -> sut.convertStudentDetails(studentList, studentCourseList));
  }

  @Test
  void 受講生コース情報のリストがnullの場合に空の受講生コース情報を持った受講生詳細のリストが返ること() {
    // 事前準備
    //　受講生のリストを作成
    Student student1 = new Student();
    student1.setId("111111111111111111111111111111111111");
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
    student2.setId("222222222222222222222222222222222222");
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

    // 受講生詳細のリストを作成
    StudentDetail studentDetail1 = new StudentDetail();
    studentDetail1.setStudent(student1);
    List<StudentCourse> studentCourseList1 = new ArrayList<>();
    studentDetail1.setStudentsCourses(studentCourseList1);

    StudentDetail studentDetail2 = new StudentDetail();
    studentDetail2.setStudent(student2);
    List<StudentCourse> studentCourseList2 = new ArrayList<>();
    studentDetail2.setStudentsCourses(studentCourseList2);

    List<StudentDetail> studentDetailList = new ArrayList<>();
    studentDetailList.add(studentDetail1);
    studentDetailList.add(studentDetail2);

    // 実行
    List<StudentDetail> expected = studentDetailList;
    List<StudentDetail> actual = sut.convertStudentDetails(studentList, null);

    // 検証
    Assertions.assertEquals(expected, actual);
  }
}
