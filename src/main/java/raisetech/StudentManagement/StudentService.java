package raisetech.StudentManagement;

import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
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
  private final Map<String, String> studentPersonalDataMap = new ConcurrentHashMap<>();

  /* Getter */
  public String getName() {
    return name;
  }

  public String getAge() {
    return age;
  }

  public Map<String, String> getStudentPersonalDataMap() {
    return studentPersonalDataMap;
  }


  /* Setter */
  public void setName(String name) {
    this.name = name;
  }

  public void setAge(String age) {
    this.age = age;
  }

  public void setStudentPersonalDataMap(String name, String age) {
    this.studentPersonalDataMap.put(name, age);
  }

  /*
    すべての受講生情報を文字列で返すメソッド

    studentMap に登録した受講生情報を "〇〇 is △△ years old." という形式の文字列にし、それぞれの文字列を
    改行コード（\n）で連結して返します。
   */
  public String getAllStudentsPersonalData() {
    return studentPersonalDataMap.entrySet().stream()
        .sorted(Map.Entry.comparingByKey(Comparator.nullsLast(new MixedCharacterComparator())))
        .map(s -> s.getKey() + " is " + s.getValue() + " years old.")
        .collect(Collectors.joining("\n"));
  }
}
