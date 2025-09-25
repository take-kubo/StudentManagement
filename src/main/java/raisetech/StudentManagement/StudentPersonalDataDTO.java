package raisetech.StudentManagement;

public class StudentPersonalDataDTO {

  private String studentName;
  private String studentAge;

  public StudentPersonalDataDTO() {
    studentName = "unknown";
    studentAge = "unknown";
  }

  /* getter */
  public String getStudentName() {
    return studentName;
  }

  public String getStudentAge() {
    return studentAge;
  }

  /* setter */
  public void setStudentName(String studentName) {
    this.studentName = studentName;
  }

  public void setStudentAge(String studentAge) {
    this.studentAge = studentAge;
  }

}
