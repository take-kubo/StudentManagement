package raisetech.StudentManagement;

import java.util.HashMap;
import java.util.Map;
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
  public Map<String, String> studentMap = new HashMap<>();

  /* Getter */
  public String getName() {
    return name;
  }

  public String getAge() {
    return age;
  }

  /* Setter */
  public void setName(String name) {
    this.name = name;
  }

  public void setAge(String age) {
    this.age = age;
  }

}
