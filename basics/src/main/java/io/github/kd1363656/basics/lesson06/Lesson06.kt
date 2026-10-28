package io.github.kd1363656.basics.lesson06

fun main()
{
    val tasks = listOf("掃除", "選択", "掃除")                // List<String>
    val tags  = setOf ("家事", "買い物", "家事")              // Set<String> : [家事, 買い物]
    val faces = mapOf ("霊夢" to "通常", "魔理沙" to "笑顔") // Nap<String, String>

    println(tasks[0])       // 掃除
    println(tasks.size)     // 3
    println("家事" in tags) // true
    println(faces["霊夢"])  // 通常(キーがなければ null)

    val fixed = listOf("掃除")
    // fixed.add("選択") // エラー : Listには add がない

    val todo = mutableListOf("掃除")
    todo.add   ("洗濯") // OK
    todo.remove("掃除")
    todo[0] = "選択物をたたむ"

    val counts = mutableMapOf<String, Int>()

    counts["霊夢"] = 3
    counts["霊夢"] = (counts["霊夢"] ?: 0) + 1 // 4

    val before  = listOf("掃除", "選択")
    val after   = before + "買い物"      // [掃除、 洗濯,  買い物] (before はそのまま)
    val removed = after - "掃除"         // [洗濯、 買い物]

    val scores = listOf(3,
                        8,
                        5,
                        1)

    scores.first()                // 3
    scores.last()                 // 1
    scores.sorted()               // [1, 3, 5, 8]
    scores.sortedDescending()     // [8, 5, 3, 1]
    scores.sum()                  // 8 (空なら null)
    scores.filter  { it >= 5 }    // [8, 5]
    scores.map     { it * 10 }    // [30, 80, 50, 10]
    scores.count   { it % 2 == 1} // 3
    scores.any     { it > 7 }     // true
    scores.all     { it > 0 }     // true
    scores.indexOf(5)             // 2
    scores.isEmpty()              // false

    // ここから練習問題
    val done = listOf(true, false, true, false)

    val doneCount     : Int = done.filter { it == true }.count()
    val finishedCount = done.size - doneCount

    println("完了件数 : ${doneCount}\n未完了件数 : ${finishedCount}")

    val charaHabitOfSaying = mapOf("霊夢" to "よ", "魔理沙" to "だぜ", "妖夢" to "です")

    val marisaHabitOfSaying = charaHabitOfSaying["魔理沙"] ?: ""

    println("魔理沙の口癖 : ${marisaHabitOfSaying}")
}