package raisetech.StudentManagement;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

/*
  受講生情報を保持するクラス

  ・@Service を付けることでこのクラスがビジネスロジック層に属することを宣言している、とのこと
  ・別のクラスでこのクラスを使うとき @Autowired を付けて宣言すれば、new しなくても使えるようになるようです
 */
@Service
public class StudentService {

  private String name = "Yamada Taro";
  private String age = "40";
  private final Map<String, String> studentMap = new ConcurrentHashMap<>();

  /* Getter */
  public String getName() {
    return name;
  }

  public String getAge() {
    return age;
  }

  public Map<String, String> getStudentMap() {
    return studentMap;
  }


  /* Setter */
  public void setName(String name) {
    this.name = name;
  }

  public void setAge(String age) {
    this.age = age;
  }

  public void setStudentMap(String name, String age) {
    this.studentMap.put(name, age);
  }

}
