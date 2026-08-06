package raisetech.StudentManagement.controller;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.data.SuccessDTO;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.service.StudentService;

/**
 * 受講生の検索や登録、更新などを行うRest APIとして受け付けるControllerです。
 */
@RestController
public class StudentController {

  private final StudentService service;

  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }

  /**
   * 受講生検索です。
   * IDに紐づく任意の受講生の情報を取得します。
   *
   * @param id 受講生ID
   * @return 受講生
   */
  @GetMapping("/students/{id}")
  public StudentDetail getStudent(@PathVariable String id) {
    return service.searchStudent(id);
  }

  /**
   * 受講生一覧検索です。
   * 全件検索を行うので、条件指定は行いません。
   *
   * @return 受講生一覧（全件）
   */
  @GetMapping("/students")
  public List<StudentDetail> getStudentList() {
    return service.searchStudentList();
  }

  @GetMapping("/courses")
  public List<StudentCourse> getStudentsCourseList() {
    return service.searchStudentsCourseList();
  }

  @PostMapping("/students")
  public ResponseEntity<SuccessDTO> registerStudent(
      @RequestBody @Valid StudentDetail studentDetail) {

    service.registerStudentInfo(studentDetail);

    SuccessDTO successDTO = new SuccessDTO("Register success.", studentDetail.getStudent().getId());

    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(studentDetail.getStudent().getId()).toUri();
    
    return ResponseEntity.status(HttpStatus.CREATED)
        .location(uri)
        .body(successDTO);

  }

  @PutMapping("/students/{id}")
  public ResponseEntity<SuccessDTO> updateStudent(
      @PathVariable("id") String id, @RequestBody @Valid StudentDetail studentDetail) {

    studentDetail.getStudent().setId(id);

    service.updateStudentInfo(studentDetail);

    SuccessDTO successDTO = new SuccessDTO("Update success.", studentDetail.getStudent().getId());
    return ResponseEntity.status(HttpStatus.OK).body(successDTO);

  }
}
