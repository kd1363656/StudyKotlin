package io.github.kd1363656.basics.lesson09

import java.time.LocalDate

// 第9章：ラムダ・高階関数・拡張関数・スコープ関数
fun main() {
    showLambda             ()
    showImplicitIt         ()
    showHigherOrderFunction()
    showFunctionReference  ()
    showCollectionChain    ()
    showExtensionFunction  ()
    showScopeFunctions     ()
    showRunAndWith         ()
}

// 「ラムダ式と関数型」
fun showLambda() {
    val twice            = { x : Int -> x * 2 }
    val f : (Int) -> Int = twice

    println(f(3))      // 6
    println(twice(10)) // 20

    val add = { a : Int, b : Int ->
        println("${a} + ${b}を計算")

        a + b // 最終の士気が戻り値
    }

    println(add(2, 3)) // 2 + 3を計算 = 5
}

// 「it」
fun showImplicitIt() {
    val isLong      : (String) -> Boolean = { it.length > 10 }
    val isLongNamed : (String) -> Boolean = { text -> text.length > 10 } // 上と同じ意味

    println(isLong("掃除"))                                // false
    println(isLongNamed("とても長いタスクのタイトルです")) // true

    var count   = 0
    val countUp = { count++ } // 外の var を書き換えられる(キャプチャ)

    countUp()
    countUp()

    println(count) // 2
}

// 「高階関数と末尾ラムダ」
fun repeatTimes(times : Int, action : (Int) -> Unit) {
    for (i in 1..times) {
        action(i) // 受け取った関数を呼ぶ
    }
}

fun showHigherOrderFunction() {
    // 普通の呼び方
    repeatTimes(3, { i -> println("${i} 回目") })

    // 最後の引数がラムダなら ( ) の外に出せる <- 末尾ラムダ
    repeatTimes(2) { i -> println("${i} 回目 (末尾ラムダ)") }

    // 引数が1つなので it も使える
    repeatTimes(2) { println("${it} 回目 (it)") }
}

// 「関数参照」
fun isNotBlankTitle(title : String) = title.isNotBlank()

fun showFunctionReference(){
    val titles     = listOf("掃除", "", "洗濯")
    val valid      = titles.filter(::isNotBlankTitle) // [掃除, 洗濯]
    val sameResult = titles.filter { isNotBlankTitle(it) }        // 上と同じ意味

    println(valid)
    println(sameResult)
}

// 「コレクション操作の組み合わせ」
data class Task(val title : String, val isDone : Boolean, val priority : Int)

fun showCollectionChain() {
    val tasks = listOf(
        Task("掃除", true, 2),
        Task("買い物", false, 1),
        Task("レポート", false ,3)
    )

    // 未完了のタスクのタイトルを、優先度の高い順に
    val todo = tasks.filter { !it.isDone }
        .sortedByDescending { it.priority }
        .map                { it.title }

    println(todo) // [レポート, 買い物]

    // 未完了を上、完了を下に(false が先に並ぶ)
    val ordered = tasks.sortedBy { it.isDone }
    println(ordered.map { it.title }) // [買い物, レポート, 掃除]

    // 完了・未完了で二つに分ける
    val (done, notDone) = tasks.partition { it.isDone }
    println("${done.size}件完了、${notDone.size}件未完了" ) // 1件完了、2件未完了

    /// 条件でグループ分け -> Map<Boolean, List<Task>>
    val grouped = tasks.groupBy { it.priority >= 2 }
    println(grouped[true]?.map { it.title }) // [掃除、レポート]

    // 1つの値にまとめる
    val titles = tasks.joinToString("、") { it.title }
    val total = tasks.sumOf                           { it.priority }
    println(titles) // 掃除、買い物、レポート
    println(total)  // 6

    // 条件に合う最初の1つ (なければ null)
    val first = tasks.firstOrNull { it.priority == 3 }
    println(first?.title) // レポート
}

// 「拡張関数」
fun LocalDate.isToday() : Boolean = this == LocalDate.now()

// List<Task> に「完了率」を足す
fun List<Task>.doneRate() : Float = if (isEmpty()) 0F else count { it.isDone }.toFloat() / size

fun showExtensionFunction() {
    val tasks = listOf(Task("掃除", true, 2), Task("買い物", false, 1))

    val rate  = tasks.doneRate()
    val today = LocalDate.of(2026, 10, 27).isToday()

    println(rate)                      // 0.5
    println(today)                     // 実行した日が 2026-10-27 なら true
    println(LocalDate.now().isToday()) // true
}

// 「スコープ関数」(その1 : let / apply / also)
// 例のための関数
fun loadTasks() : List<String> = listOf("掃除", "洗濯")

fun showScopeFunctions() {
    val memo : String? = "牛乳を買う"

    // let : null でなければ
    memo?.let { println("メモ : $it") }

    // apply : 設定してそのまま返す
    val builder = StringBuilder().apply {
        append("おはよう")
        append("!")
    }

    // also : 途中でログを出す
    val tasks = loadTasks().also { println("${it.size}件読み込み") }

    // buildString (applyと似た便利関数)
    val message = buildString {
        append("教のタスクは")
        append(tasks.size)
        append("件です")
    }

    println(message) // 今日のタスクは2件です
}

// 「スコープ関数」(その2 : run / width)
fun showRunAndWith() {
    val tasks = loadTasks()

    // run : オブジェクトを使って計算した結果を返す
    val summary = tasks.run {
        "最初は${first()}, 全部で${size}件" // this.first()、this.size の this. を省略
    }

    println(summary) // 最初は掃除、全部で2件

    // with : 引数にオブジェクトを渡す形。中身は run 遠内
    val count = with(tasks) {
        size * 10
    }

    println(count) // 20
}