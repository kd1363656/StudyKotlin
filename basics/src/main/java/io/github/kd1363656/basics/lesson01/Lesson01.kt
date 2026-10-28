package io.github.kd1363656.basics.lesson01

fun main() {
    println("Hello")

    // 一行コメント
    /* 複数行
    *  コメント*/
    /**
     * kDoc(ドキュメントコメント)。関数やクラスの説明を書く。
     * @param name 名前
     */

    val name      = "霊夢" // 再代入できない
    var doneCount = 0      // 再代入できる

    doneCount++        // OK
    // name = "魔理沙" // コンパイルエラー : val は再代入できない

    println("${name} : ${doneCount} 件完了。")

    val list = mutableListOf("掃除")
    list.add("買い物")        // OK : リストの中身は変えられる
    // list = mutbaleListOf() // NG : 別のリストを代入するのは不可

    // 型の明示は(val or var) 変数名 : 型という書き方
    val count : Int    = 3      // Int と推論される
    val rate  : Float  = 0.5F   // Doubleと推論される
    val title : String = "掃除" // Stringと推論される

    // 参考演算子はないため以下の書き方になる
    val label = if(doneCount > 0) "えらい" else "頑張ろう"

    // ここから練習問題(コードのみ)
    // 問2
    val charaName : String = "魔理沙"
    val taskCount : Int    = 3

    println("${charaName}:残り${taskCount}件だぜ!")
}