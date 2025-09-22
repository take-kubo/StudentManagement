package raisetech.StudentManagement;

import java.text.Collator;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/*
  日本語、数字、ローマ字が混在した文字列（String）の順序を比較するクラス

  Comparator インターフェースの実装であり、compare メソッドをオーバーライドしています。
 */
public class MixedCharacterComparator implements Comparator<String> {

  /*
    Collator クラスのフィールド

    同じ種類の文字を正しく比較するために必要なクラスです。compare メソッド内で使用します。
   */
  private final Collator japaneseCollator;  // 日本語用
  private final Collator englishCollator;   // ローマ字用

  /*
   コンストラクタ

   MixedCharacterComparator クラス（このクラス）をインスタンス化するときに１回だけ実行されるので、
   Collator クラスのインスタンスがひとつだけ生成されます。compare メソッドではこれを使い回します。

   ※compare メソッド内でインスタンス化すると compare で文字列を比較するたびに Collator クラスの
   　インスタンスが生成されます。呼び出し側でソートをするとき compare メソッドは何回も呼ばれます。
   　結果として動作速度が遅くなったり、メモリを圧迫したりするようです。
   */
  public MixedCharacterComparator() {
    this.japaneseCollator = Collator.getInstance(Locale.JAPANESE);
    this.englishCollator = Collator.getInstance(Locale.ENGLISH);
  }

  /*
    compare メソッドの実装

    引数に渡した２つの文字列 str1 と str2 の順番を比較します。
    比較の方法は次の通り。まず先頭の文字同士を比較、つぎに２番目の文字同士を比較、つぎに３番目の文字同士を比較、
    つぎに・・・、と、短い方の文字列の最後まで比較します。
    文字同士の比較基準は、日本語同士は辞書順、数字同士は小さい順、ローマ字同士はアルファベット順で、
　  異種の文字列同士の場合は、日本語 ⇒ 数字 ⇒ ローマ字 の順です。
    その結果、str1 ⇒ str2 の順なら負の値を、str1 = str2 なら 0 を、str2 ⇒ str1 なら正の値を返します。
   */
  @Override
  public int compare(String str1, String str2) {

    // 引数の文字列を分割し、文字列の要素をリストに格納する
    List<String> partsOfStr1 = getDividedStringFrom(str1);
    List<String> partsOfStr2 = getDividedStringFrom(str2);

    // 短い方の文字列の長さを取得する（forループの回数を決めるため）
    int sizeOfSmallerList = Math.min(str1.length(), str2.length());

    // 文字を1個ずつ取り出し順序を比較する
    for (int i = 0; i < sizeOfSmallerList; i++) {

      // 文字の種類を取得する
      String typeOfPart1 = getTypeOf(partsOfStr1.get(i));
      String typeOfPart2 = getTypeOf(partsOfStr2.get(i));

      // 文字の組み合わせごとに、比較する
      // 文字が異なる場合は結果が確定し、同じ場合は次の文字の比較に進む
      if (typeOfPart1.equals("Japanese") && typeOfPart2.equals("Japanese")) {

        // 文字が「日本語」と「日本語」の場合なので辞書順で比較する
        int comparisonResult = japaneseCollator.compare(partsOfStr1.get(i), partsOfStr2.get(i));
        if (comparisonResult != 0) {
          return comparisonResult;
        }

      } else if (typeOfPart1.equals("Japanese") && typeOfPart2.equals("Number")) {

        // 「日本語」⇒「数字」の順なので負の整数を返す
        return -1;

      } else if (typeOfPart1.equals("Japanese") && typeOfPart2.equals("Romaji")) {

        // 「日本語」⇒「ローマ字」の順なので負の整数を返す
        return -1;

      } else if (typeOfPart1.equals("Number") && typeOfPart2.equals("Japanese")) {

        // 「数字」⇒「日本語」の順なので正の整数を返す
        return 1;

      } else if (typeOfPart1.equals("Number") && typeOfPart2.equals("Number")) {

        // 文字が「数字」と「数字」の場合なので、まず Integer 型に変換する
        int number1 = Integer.parseInt(partsOfStr1.get(i));
        int number2 = Integer.parseInt(partsOfStr2.get(i));

        // そして数として自然な大小で比較する
        int comparisonResult = Integer.compare(number1, number2);
        if (comparisonResult != 0) {
          return comparisonResult;
        }

      } else if (typeOfPart1.equals("Number") && typeOfPart2.equals("Romaji")) {

        // 「数字」⇒「ローマ字」の順なので負の整数を返す
        return -1;

      } else if (typeOfPart1.equals("Romaji") && typeOfPart2.equals("Japanese")) {

        // 「ローマ字」⇒「日本語」の順なので正の整数を返す
        return 1;

      } else if (typeOfPart1.equals("Romaji") && typeOfPart2.equals("Number")) {

        // 「ローマ字」⇒「数字」の順なので正の整数を返す
        return 1;

      } else if (typeOfPart1.equals("Romaji") && typeOfPart2.equals("Romaji")) {

        // 文字が「ローマ字」と「ローマ字」の場合なので、アルファベット順で比較する。
        int comparisonResult = englishCollator.compare(partsOfStr1.get(i), partsOfStr2.get(i));
        if (comparisonResult != 0) {
          return comparisonResult;
        }

      }

    }

    // 先頭から短い方の文字列の末尾まで文字が同じだった場合（例：abc, abcdef）
    // 「短い文字列」⇒「長い文字列」の順番（例：abc ⇒ abcdef）
    return str2.length() - str1.length();
  }


  /*
    文字列を1文字ずつ取り出すメソッド

    引数に渡した文字列を先頭から１文字ずつ取り出しリストにします。
    例）"あいう123ABC"　⇒　"あ", "い", "う", "1", "2", "3", "A", "B", "C"
   */
  private List<String> getDividedStringFrom(String targetString) {
    return Arrays.stream(targetString.split("")).toList();
  }


  /*
    文字列の種類を判別するメソッド

    引数に渡した文字列が日本語の場合は Japanese という文字列を、数字の場合は Number という文字列を、
    ローマ字の場合は Romaji という文字列を、それ以外の文字列の場合は Other を返します。
   */
  private String getTypeOf(String targetString) {
    if (targetString.matches("^[ぁ-んァ-ヶｱ-ﾝﾞﾟ一-龠ー]+$")) {
      return "Japanese";
    } else if (targetString.matches("^[0-9]+$")) {
      return "Number";
    } else if (targetString.matches("^[a-zA-Z]+$")) {
      return "Romaji";
    } else {
      return "Other";
    }
  }

}
