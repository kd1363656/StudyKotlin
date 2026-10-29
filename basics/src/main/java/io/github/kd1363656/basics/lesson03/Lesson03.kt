package io.github.kd1363656.basics.lesson03

// 第3章 : 制御構文 (if / when / for / while)
fun main()
{
    showIfStatement    ()
    showIfExpression   ()
    showWenGreeting    ()
    showWhenVariantions()
    showRanges         ()
    showForLoops       ()
    showWhileLoops     ()
    showLabeledBreak   ()

    // ここから問題
    exerciseOne()
    exerciseTwo()
}

// 「if 式」(その1 : 分として使う)
fun showIfStatement() {
    val remaining = 3

    if (remaining == 0) {
        println("全部終わった!")
    }
    else if(remaining <= 2) {
        println("あと少し")
    }
    else {
        println("頑張ろう") // remaining は 3 なので、ここが実行される
    }
}

// 「if 式」(その2 : 式として使う)
fun showIfExpression() {
    val remaining = 0

    // 値を返す式として使う (C++の参考演算子画代わり)
    val face = if (remaining == 0) "笑顔" else "通常"
    println(face) // 笑顔

    // ブロックの場合は「最後の式」が値になる
    val message = if(remaining == 0){
        val bonus = "お疲れ様"
        "全部完了!$bonus" // これが message に入る
    }
    else{
        "残り${remaining}件"
    }

    println(message) // 全部完了!お疲れ様
 }

// 「when」の書き方
fun showWenGreeting() {
    val hour = 9
    val greeting = when (hour) {
        in 5..10  -> "おはよう"
        in 11..17 -> "こんにちは"
        else             -> "こんばんは"
    }
    println(greeting) // おはよう
}

// 「when の書き方色々」
fun showWhenVariantions() {
    // 例のための値
    val dayOfWeek   = 6
    val value : Any = "霊夢" // Any は 「どんな型の値出も入る型」
    val remaining   = 3
    val total       = 10

    // 複数の値をまとめる
    when (dayOfWeek) {
        6,7  -> println("休日")
        else -> println("平日")
    }

    // 型で分ける (第5章のスマートキャストと組み合わせる)
    when (value) {
        is String -> println("文字列 : ${value.length}文字")
        is Int    -> println("整数   : $value")
    }

    // 引数なし : if - else if の代わりに条件を並べる
    val comment = when {
        remaining == 0        -> "完璧!"
        remaining < total / 2 -> "半分以上終わった"
        else                  -> "これから"
    }
    println(comment) // 半分以上終わった
}

// 「範囲と for」(その1 : 範囲)
fun showRanges() {
    for (i in 1..5) {
        print("$i ") // 1 2 3 4 5
    }
    println()

    for (i in 0 until 5) {
        print("$i ") // 0 1 2 3 4
    }
    println()

    for (i in 5 downTo 1) {
        print("$i ") // 5 4 3 2 1
    }
    println()

    for (i in 0..10 step 2) {
        print("$i ") // 0 2 4 6 8 10
    }
    println()
}

// 「範囲と for」(その2 : リストを回す)
fun showForLoops() {
    val tasks = listOf("掃除", "洗濯", "買い物") // 3つの文字列のリスト (第6章)

    for (task in tasks) {
        println(task)
    }

    // 添え字も欲しいとき
    for ((index, task) in tasks.withIndex()) {
        println("${index + 1}. $task") // 1. 掃除 2. 洗濯 3. 買い物
    }

    // 添え字だけ
    for (i in tasks.indices) { // 0 until tasks.sizeと同じ
        println("$i : ${tasks[i]}")
    }

    // 1月 ~ 12月
    for (month in 1..12) {
        print("${month}月 ")
    }
    println()
}

// 「while と break / continue」
fun showWhileLoops() {
    var count = 0

    while (count < 3) {
        count++
    }
    println(count)

    do {
        println("少なくとも1回は実行される")
    } while (false)

    // continue : 残りを飛ばして次の下位へ。。break : ループを抜ける
    for (i in 1..10)
    {
        if (i % 2 == 0) {
            continue // 偶数は飛ばす
        }
        if (i > 7){
            break    // 7 を超えたら終わり
        }

        print("$i ") // 1 3 5 7
    }
    println()
}

// 「while と break / continue」(その2 : ラベル付き break)
fun showLabeledBreak() {
    outer@ for (week in 1..5) {
        for (day in 1..7) {
            if (week == 2 &&
                day  == 3) {
                println("${week}週目の${day}日目で終了")
                break@outer // 両方のループを抜ける
            }
        }
    }

    println("ループの外に出た")
}

fun exerciseOne() {
    val hour = 0

    val text = when (hour) {
        in 5..10  -> "おはよう"
        in 11..17 -> "こんにちは"
        else             -> "こんばんは"
    }

    println("挨拶 : ${text}")
}

fun exerciseTwo() {
    for (month in 1..12) {
        println("${month}月")
    }

    for (month in 1..12) {

        if (month % 2 != 0) {
            continue
        }

        println("${month}月")
    }
}