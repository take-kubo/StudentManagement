package raisetech.StudentManagement.controller.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;

/**
 * コントローラ用にデータの詰め替えを担当するコンバータ。
 */
@Slf4j
@Component
public class StudentConverter {

  /**
   * 受講生詳細情報のリストを生成する。
   *
   * <p>受講生情報と受講生コース情報を結合して受講生詳細情報を生成します。
   * 受講生情報のIDがnullの場合NPEを投げます。
   * 受講生コース情報が存在しない場合、空の受講生コース情報を使って受講生詳細情報を生成します。
   * どちらの場合でもエラーログを出力します。</p>
   *
   * @param studentList 受講生情報のリスト
   * @param studentCourseList 受講生コース情報のリスト
   * @return 受講生詳細情報のリスト
   * @throws NullPointerException 受講生情報のIDがnullの場合
   */
  public List<StudentDetail> convertStudentDetails(List<Student> studentList,
      List<StudentCourse> studentCourseList) throws NullPointerException {

    // 受講生コース情報のリストがnullでないかチェックしています
    // nullだった場合は初期化された受講生コース情報をひとつ持つリストにしています
    try {
      Objects.requireNonNull(studentCourseList);
    } catch (NullPointerException error) {
      log.error(
          "The studentCourseList list is null.",
          error);
      StudentCourse emptyStudentsCourse = new StudentCourse();
      studentCourseList = List.of(emptyStudentsCourse);
    }

    List<StudentDetail> studentDetailList = new ArrayList<>();

    for (Student student : studentList) {
      StudentDetail studentDetail = new StudentDetail();
      studentDetail.setStudent(student);

      // 受講生情報のID（student.getId()）がnullでないかチェックしています
      // nullの場合はNPEを投げます
      try {
        Objects.requireNonNull(student.getId());
      } catch (NullPointerException error) {
        log.error(
            "The id field of student is null. The studentList table in studentmanagement database includes null id.",
            error);
        throw new NullPointerException();
      }

      List<StudentCourse> convertStudentCourseList = new ArrayList<>();
      for (StudentCourse studentCourse : studentCourseList) {

        if (student.getId().equals(studentCourse.getStudentId())) {
          convertStudentCourseList.add(studentCourse);
        }
      }

      studentDetail.setStudentsCourses(convertStudentCourseList);
      studentDetailList.add(studentDetail);
    }

    return studentDetailList;

  }

}
