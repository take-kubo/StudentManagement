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
    比較基準は、日本語同士は辞書順、数字同士は小さい順、ローマ字同士はアルファベット順、その他同士はUnicode順で、
　  異種の文字同士の場合は、日本語 ⇒ 数字 ⇒ ローマ字 ⇒ その他 の順です。
    結果、str1 ⇒ str2 の正順なら負の整数を、str1 = str2 なら 0 を、str2 ⇒ str1 の逆順なら負の整数を返します。
   */
  @Override
  public int compare(String str1, String str2) {

    // 入力チェック
    // str1 が null なら正の値, str2 がnullなら負の値を返します
    if (str1 == null) {
      return 1;
    }
    if (str2 == null) {
      return -1;
    }

    // 引数の文字列を分割し、文字列の要素をリストに格納する
    char[] partsOfStr1 = str1.toCharArray();
    char[] partsOfStr2 = str2.toCharArray();

    // 短い方の文字列の長さを取得する（forループの回数を決めるため）
    int sizeOfSmallerList = Math.min(str1.length(), str2.length());

    // 文字を1個ずつ取り出し順序を比較する
    // 異なる文字の場合は結果が return され、同じ文字の場合は次の文字の比較に進む
    for (int i = 0; i < sizeOfSmallerList; i++) {

      // 文字の種類を取得する
      String typeOfPart1 = getTypeOf(String.valueOf(partsOfStr1[i]));
      String typeOfPart2 = getTypeOf(String.valueOf(partsOfStr2[i]));


      // 1. 文字の種類が同じ場合
      // 文字が「日本語」同士の場合は、辞書順で比較する
      if (typeOfPart1.equals("Japanese") && typeOfPart2.equals("Japanese")) {
        int comparisonResult = japaneseCollator.compare(
            String.valueOf(partsOfStr1[i]), String.valueOf(partsOfStr2[i]));

        if (comparisonResult != 0) {
          return comparisonResult;
        }
      }

      // 文字が「数字」同士の場合は、自然な大小で比較する
      if (typeOfPart1.equals("Number") && typeOfPart2.equals("Number")) {
        int number1 = Character.getNumericValue(partsOfStr1[i]);
        int number2 = Character.getNumericValue(partsOfStr2[i]);

        int comparisonResult = Integer.compare(number1, number2);
        if (comparisonResult != 0) {
          return comparisonResult;
        }
      }

      // 文字が「ローマ字」同士の場合は、アルファベット順で比較する。
      if (typeOfPart1.equals("Romaji") && typeOfPart2.equals("Romaji")) {
        int comparisonResult = englishCollator.compare(
            String.valueOf(partsOfStr1[i]), String.valueOf(partsOfStr2[i]));

        if (comparisonResult != 0) {
          return comparisonResult;
        }
      }

      // 文字が「その他」同士の場合なので、Unicode順で比較する
      if (typeOfPart1.equals("Other") && typeOfPart2.equals("Other")) {
        int comparisonResult = Character.compare(partsOfStr1[i],partsOfStr2[i]);
        if (comparisonResult != 0) {
          return comparisonResult;
        }
      }

      // 2. 文字の種類が異なる場合
      // 「日本語」⇒「数字」⇒「ローマ字」⇒「その他」の順番を「正順」、逆の順番を「逆順」と呼ぶ
      //  正順の場合は負の整数を返し、逆順の場合は正の整数を返す
      if (typeOfPart1.equals("Japanese") && typeOfPart2.equals("Number")) return -1;
      if (typeOfPart1.equals("Japanese") && typeOfPart2.equals("Romaji")) return -1;
      if (typeOfPart1.equals("Japanese") && typeOfPart2.equals("Other"))  return -1;

      if (typeOfPart1.equals("Number") && typeOfPart2.equals("Japanese")) return  1;
      if (typeOfPart1.equals("Number") && typeOfPart2.equals("Romaji"))   return -1;
      if (typeOfPart1.equals("Number") && typeOfPart2.equals("Other"))    return -1;

      if (typeOfPart1.equals("Romaji") && typeOfPart2.equals("Japanese")) return  1;
      if (typeOfPart1.equals("Romaji") && typeOfPart2.equals("Number"))   return  1;
      if (typeOfPart1.equals("Romaji") && typeOfPart2.equals("Other"))    return -1;

      if (typeOfPart1.equals("Other") && typeOfPart2.equals("Japanese"))  return  1;
      if (typeOfPart1.equals("Other") && typeOfPart2.equals("Number"))    return  1;
      if (typeOfPart1.equals("Other") && typeOfPart2.equals("Romaji"))    return  1;

    }

    // 先頭から短い方の文字列の末尾まで文字が同じだった場合（例：abc, abcdef）
    // 「短い文字列」⇒「長い文字列」の順番（例：abc ⇒ abcdef）
    return str1.length() - str2.length();
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
