package io.github.kd1363656.basics.lesson01

// 第1章 : Kotlinの第一歩(main, コメント, val / var, 型推論, 文と式)
// 節を1つ書き終えるたびに、その父子の行の // を外して実行する
fun main() {
    showHello         ()
    showComments      ()
    showValAndVar     ()
    showTypeInference ()
    showTypeAnnotation()
    showIfExpression  ()

    // ここから問題
    exerciseOne()
}

// 最初のプログラム
fun showHello() {
    println("Hello")
    print  ("print は改行しない。")
    print  ("ので、続けて表示される")
    println()                         // なにも渡さない println は、改行だけする
    println("Kotlin")
}

/**
 *  KDoc (ドキュメントコメント)。関数やクラスのすぐ上に書いて、説明を残す
 *  Android Studio で関数名にマウスを乗せると、この説明が表示される
 */
fun showComments() {
    // 1行コメント : // から行の最後までが無視される
    println("コメントは実行されない") // 行の途中から書いても良い

    /* 複数行コメント :
       ここに書いた行は、全部無視される */
}

// [val と var]
fun showValAndVar() {
    val name      : String = "霊夢" // 再代入できない
    var doneCount : Int    = 0      // 再代入できる

    doneCount = doneCount + 1 // OK : var なので新しい値を入れられる
    doneCount += 1            // C++ と 同じく += も使える
    // name = "魔理沙"       // コンパイルエラー : val は再代入できない

    println("${name} : ${doneCount} 件完了") // 霊夢 : 2 件完了

//    // valは「値の変更を禁止する」だけで中身の変更は禁止しない
//    val list = mutableListOf<String>("掃除")
//    list.add("買い物")                 // OK : リストの中身は変えれる
//    // list = mutableListof("買い物") // NG : 別のリストを代入するのは不可
}
// 「型推論と型注釈」(その1 : 型推論)
fun showTypeInference() {
    val count     = 3      // Intと推論される (整数はまずInt)
    val bigCount  = 3L     // 最後にLをつけると Long
    val rate      = 0.5    // Double と推論される(少数はまず Double)
    val smallRate = 0.5F   // 最後に f をつけると Float
    val title     = "掃除" // String と推論される

    println("${count} ${bigCount} ${rate} ${smallRate} ${title}") // 3, 3, 0.5, 0.5, 掃除
}

// 「型推論と型注釈」(その2 : 型注釈)
fun showTypeAnnotation() {
    val count : Int   = 3
    val rate  : Float = 0.5F
    val total : Long  = 10  // 型を Long と書いたので、 3L のように L を付けなくてよい

    val message : String // 後で代入するなら型注釈が必要

    message = "おはよう" // 使う前に必ず値を入れる

    println("${count}, ${rate} ${total} ${message}") // 3, 0.5, 10, おはよう
}

fun showIfExpression() {
    val doneCount = 1
    val label = if (doneCount > 0) "えらい！" else "頑張ろう"
    println(label) // えらい
}

fun exerciseOne() {
    val name    : String = "魔理沙"
    val taskNum : Int    = 3

    println("${name} : 残り${taskNum}件だぜ")
}