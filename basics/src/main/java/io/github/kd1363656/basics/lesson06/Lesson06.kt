package io.github.kd1363656.basics.lesson06

// 第6章：コレクション（List / Set / Map）
fun main() {
    showThreeCollections    ()
    showLoopOverCollections ()
    showMutableCollections  ()
    showNewCollections      ()
    showCollectionOperations()
    showArray               ()

    // ここから問題
    exerciseOne()
    exerciseTwo()
}

// [3つのコレクション](その1 : 作る・読む)
fun showThreeCollections(){
    val tasks = listOf("掃除", "洗濯",   "掃除") // List<String>
    val tags  = setOf ("家事", "買い物", "家事") // Set<String> : [家事、買い物]

    val faces = mapOf ("霊夢" to "通常", "魔理沙" to "笑顔") // Map<String, String>

    println(tasks) // [掃除, 洗濯, 掃除]
    println(tags)  // [家事、買い物](重複した 「家事」  は一つになる)
    println(faces) // { 霊夢 = 通常、魔理沙 = 笑顔 }

    println(tasks[0])       // 掃除
    println(tasks.size)     // 3
    println("家事" in tags) // true
    println(faces["霊夢"])  // 通常
    println(faces["妖夢"])  // null (キーがなければ null)
}

// 「3つのコレクション」(その2 : forで回す)
fun showLoopOverCollections(){
    val tags = setOf("家事", "買い物")
    for (tag in tags){
        println("タグ : $tag")
    }

    val faces = mapOf("霊夢" to "通常", "魔理沙" to "笑顔")

    for ((name, face) in faces) {
        println("$name の表情は $face")
    }
}

// 「読み取り専用と変更可能」
fun showMutableCollections() {

    val fixed = listOf("掃除")
    // fixed.add("洗濯") // エラー : List には add がない

    val todo = mutableListOf("掃除")

    todo.add   ("洗濯") // OK : 最後に追加 -> [掃除、洗濯]
    todo.remove("掃除") // 値を指定して削除 -> [洗濯]
    todo[0] = "洗濯ものをたたむ" // 添え字で書き換え -> [洗濯ものをたたむ]
    println(fixed) // [掃除]
    println(todo)  // [洗濯物をたたむ]

    val counts = mutableMapOf<String, Int>()   // 空の Map を作る (型の指定が必要)
    counts["霊夢"] = 3                         // キー「霊夢」に 3 を入れる
    counts["霊夢"] = (counts["霊夢"] ?: 0) + 1 // 今の値に 1 を足す
    println(counts)                            // { 霊夢 = 4 }
}

// 「変更」の代わりに「新しい物を作る」
fun showNewCollections() {
    val before  = listOf("掃除", "洗濯")
    val after   = before + "買い物" // [掃除, 洗濯, 買い物]
    val removed = after  - "掃除"   // [洗濯、買い物]

    println(before) // [掃除、洗濯] (before はそのまま)
    println(after)
    println(removed)
}

// 「よく使う操作」
fun showCollectionOperations() {
    val scores = listOf(3,
                        8,
                        5,
                        1)

    println(scores.first())                // 3
    println(scores.last())                 // 1
    println(scores.sorted())               // [1, 3, 5, 8]
    println(scores.sortedDescending())     // [8, 5, 3, 1]
    println(scores.sum())                  // 17
    println(scores.maxOrNull())            // 8 (空なら null)
    println(scores.filter { it >= 5 })     // [8, 5]
    println(scores.map    { it * 10 })     // [30, 80, 50, 10]
    println(scores.count  { it % 2 == 1 }) // 3
    println(scores.any    { it > 7 })      // true
    println(scores.all    { it > 0 })      // true
    println(scores.indexOf(5))             // 2
    println(scores.isEmpty())              // false
    println(scores)                        // [3, 8, 5, 1](元のリストは変わらない)
}

// 「配列(Array)について」
fun showArray(){
    val numbers = intArrayOf(1, 2, 3) // IntArray (C++ の int[3] に近い)

    numbers[0] = 10

    println(numbers.contentToString()) // [10, 2, 3]
    println(numbers)                   // [I@1b6d3586 のような表示になる (中身ではない)]

    val other = intArrayOf(10, 2, 3)
    println(numbers == other)             // false : 配列の == は「同じオブジェクトか」を比べる
    println(numbers.contentEquals(other)) // true  : 中身を比べるには contentEquals

    val list = numbers.toList() // Listに変換すると扱いやすい
    println(list)               // [10, 2, 3]
}

fun exerciseOne() {
    val done : List<Boolean> = listOf(true, false, true, true)

    val finishedSize = done.filter { it == true }.size
    val notYetSize   = done.size - finishedSize

    println("完了数   : ${finishedSize}")
    println("未完了数 : ${notYetSize}")
}

fun exerciseTwo() {
    val charaHabitOfSayings : Map<String, String> = mapOf("霊夢" to "よ", "魔理沙" to "だぜ", "妖夢" to "です")

    val marisaHabitOfSaying : String? = charaHabitOfSayings["魔理沙"]

    println("口癖 : ${marisaHabitOfSaying ?: "口癖なし"}")
}