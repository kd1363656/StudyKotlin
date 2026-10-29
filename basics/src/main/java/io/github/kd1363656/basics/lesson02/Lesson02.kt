package io.github.kd1363656.basics.lesson02

// 第2章 : 基本の型・演算子・文字列
fun main() {
    showBasicTypes      ()
    showTypeConversion  ()
    showIntegerDivision ()
    showOperators       ()
    showEquality        ()
    showStringTemplate  ()
    showStringOperations()
    showMultilineString ()

    // ここから問題
    exerciseOne()
    exerciseTwo()
}

// 「基本の片」
fun showBasicTypes() {
    val taskCount       : Int     = 42
    val population      : Int     = 1_000_000          // _ は読みやすくするための区切り。値は 1000000
    val createdAtMillis : Long    = 1_700_000_000_000L
    val rate            : Double  = 0.75
    val ringAngle       : Float   =  270F
    val isDone          : Boolean = false
    val initial         : Char    = '霊'               // Char は ' ' で囲む
    val title           : String  = "掃除"             // String は " "で囲む

    println("${taskCount} ${population} ${createdAtMillis}")
    println("${rate}      ${ringAngle}  ${isDone} ${initial} ${title}")

    // 数値にもメソッドがある (全ての型がクラスとして扱われる)
    println(taskCount.toString() + "件") // 42件
    println(Int.MAX_VALUE)               // 2147483647 (Int で表される最大値)
}

// 「型の変換」
fun showTypeConversion() {
    val done  : Int = 3
    val total : Int = 4

    // val rate : Double = done // エラー : Int は自動で Double にならない
    val rate : Double = done.toDouble() / total
    println(rate) // 0.75

    val percent = (rate * 100).toInt() // 小数点以下は切り捨て
    println(percent)                   // 75

    val text = percent.toString() // 数値 -> 文字列
    println(text + "%")

    val number = "12".toInt() // 文字列 -> 数値(数字出ない文字列なら例外)
    println(number + 1)       // 13

    val safe = "abc".toIntOrNull() // 変換できなければ null(第6章)
    println(safe)                  // null
}

// 「整数同士の割り算」
fun showIntegerDivision() {
    val done  = 3
    val total = 4

    println(done / total)           // 0    : 整数同士なので切り捨て
    println(done % total)           // 3    : 割り算の余り
    println(done.toFloat() / total) // 0.75 : 先に Float に変換する
    println(done * 100 / total)     // 75   : 先に100を掛けると整数のままでも求められる
}

// 「演算子」
fun showOperators() {
    var count = 10
    count++            // 11
    count -= 3         // 8
    println(count % 3) // 2(余り)

    val isMorning = true
    val hasTask   = count > 0

    println(isMorning && hasTask) // true  : 両方 true
    println(!hasTask)             // false : 反対にする

    val flags = 0b0101        // 0b で2進数 (C++ と同じ)
    println(flags and 0b0100) // 4 : C++ の flags & 0b0100
    println(1 shl 3)          // 8 : C++ の1 << 3

    println(5 in 1..10)   // true : 5 は 1 ~ 10 の範囲に入っている
    println(15 !in 1..10) // true : 15 は範囲に入っていない
}

// 「== と ===」
fun showEquality() {
    val a = "TouDo"
    val b = StringBuilder("Tou").append(("Do")).toString() // 実行中に組み立てた、別の"TouDo"

    println(a == b)  // true  : 中身が同じ
    println(a === b) // false : 別のオブジェクト
    println(a != b)  // false  : != は == の反対
}

// 「文字列テンプレート」
fun showStringTemplate() {
    val name  = "妖夢"
    val done  = 2
    val total = 5

    println("${name} : ${done} / ${total} 件") // 妖夢 : 2 / 5 件
    println("残り${total - done}件です")       // 残り3件です(計算は${ }で囲む)
    println("名前の長さは${name.length}文字")  // 名前の長さは2文字
    println("価格は \$100")                    // 価格は $100 ($ そのものを出すときは \$)
}

// 「文字列の良く使う操作」
fun showStringOperations() {
    val s = "   今日のタスク   "

    println("[" + s.trim() + "]") // [教のタスク] (前後の空白を削除)
    println(s.isBlank())          // false (空白だけなら true)
    println("   ".isBlank())      // true
    println("".isEmpty())         // true (長さ0なら true)

    println("掃除、洗濯".split(",")) // [掃除、洗濯]
    println("TouDo".lowercase())                  // toudo
    println("TouDo".uppercase())                  // TOUDO
    println("TouDo".startsWith("Tou"))   // true
    println("TouDo".contains(("uD")))    // true
    println("掃除" + "する")                     // 掃除する
    println("ab".repeat(3))                 // ababab
    println("TouDo".length)                      // 5
    println("TouDo"[0])                          // T (C++ と同じく [ ] で1文字取だせる)
}

fun showMultilineString() {
    val greeting = """
        おはよう!
        今日も一日頑張ろう
        """.trimIndent() // 先頭の共通インデントと、最初と最後の空行を取り除く
    println(greeting)
}

fun exerciseOne() {
    val done  = 2
    val total = 3

    println("進捗 ${((done.toFloat() / total.toFloat()) * 100.0F).toInt()}%")
}

fun exerciseTwo() {
    println("5".toIntOrNull())
    println("x".toIntOrNull())
}