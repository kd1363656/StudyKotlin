package io.github.kd1363656.basics.lesson02

fun main() {
    var done  : Int = 3
    var total : Int = 4

    // val rate: Double = done // エラー : Int は自動で Double に暗黙的変換をされない
    val rate : Double = done.toDouble() / total // 0.75

    val percent = (rate * 100).toInt() // 75(小数点以下は切り捨て)
    val text    = percent.toString()   // "75"
    val number  = "12".toInt()         // 12(数字出ない文字列なら例外)
    val safe    = "abc".toIntOrNull()  // null (失敗したらnull)

    val a = "TouDo"
    val b = StringBuilder("Tou").append("Do").toString()

    println(a == b)  // true : 中身が同じ
    println(a === b) // false : 別のオブジェクト

    val name  = "妖夢"

    done  = 2
    total = 5

    println("${name} : ${done} / ${total}件。") // 妖夢 : 2 / 5 件
    println("残り${total - done}件です。")      // 式は${ } で囲む
    println("名前の長さは${name.length}文字")

    // よく使う文字列操作
    val s = "  教のタスク  "
    s.trim()    // "教のタスク"(前後の空白を削除)
    s.isBlank() // 空白だけならtrue
    "掃除、選択".split(",") // ["掃除", "選択"]
    "TouDo".lowercase()                  // "toudo"
    "TouDo".startsWith("Tou")   // true
    "掃除" + "する"                     // 掃除する
    "ab".repeat(3)                 // "ababab"

    val greeting = """おはよう!今日も頑張ろう。""".trimIndent() // 先頭の共通インデントを取り除く

    // ここから練習問題
    done  = 2
    total = 3

    val progress : Float = done.toFloat() / total.toFloat()

    println("進捗 ${(progress * 100).toInt()}%")

    println("${"5".toIntOrNull()}")
    println("${"x".toIntOrNull()}")
}