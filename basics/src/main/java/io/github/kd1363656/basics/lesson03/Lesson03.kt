package io.github.kd1363656.basics.lesson03

fun main() {
    // if分の書き方
    var remaining = 3

    if(remaining == 0) {
        println("全部終わった!")
    }
    else if (remaining <= 2) {
        println("あと少し")
    }
    else {
        println("頑張ろう")
    }

    // 値を返す式として使う(C++の参考演算子の代わり)
    val face = if (remaining == 0) "笑顔" else "通常"

    // ブロックの場合は「最後の式」が値になる
    val message = if (remaining == 0) {
        val bonus = "おつかれさま"
        "全部完了！$bonus"
    }
    else {
        "残り${remaining}件"
    }

    val dayOfWeek   = 6
    val value : Any = "霊夢" // Anyは「どんな型の値でも入る型」
    val total       = 10

    remaining   = 3

    // 複数の値をまとめる
    when (dayOfWeek) {
        6,7  -> println("休日")
        else -> println("平日")
    }

    // 型で分ける(第6諸王のスマートキャストと組み合わせる)
    when(value) {
        is String -> println("文字列 : ${value.length}文字")
        is Int    -> println("整数   : $value")
    }

    // 引数なし : if-else ifの代わりに条件を並べる
    val comment = when {
        remaining == 0        -> "完璧!"
        remaining < total / 2 -> "半分以上終わった"
        else                  -> "これから"
    }

    println(comment)

    val tasks = listOf("掃除", "選択", "買い物")

    for (task in tasks) {
        println(task)
    }

    // 添え字も欲しいとき
    for((index, task) in tasks.withIndex()) {
        println("${(index + 1)}. $task")
    }

    // 添え字だけ
    for (i in tasks.indices) {
        println(i)
    }

    // 1月 ~ 12月
    for(month in 1..12) {
        println("${month}月")
    }

    // whileとbreak/continue
    var count = 0

    while (count < 3) {
        count++
    }

    do {
        println("少なくとも1回は実行される")
    }
    while (false)

    outer@ for (week in 1..5) {
        for (day in 1..7) {
            if (week == 2 && day == 3) break@outer // 両方のループを抜ける
        }
    }

    // ここから練習問題
    val hour : Int = 0

    val greeting = when (hour) {
        in 5..10 -> println("おはよう")
        in 11..17 -> println("こんにちは")
        else   -> println("こんばんは")
    }

    for (month in 1..12) {
        println("${month}")
    }

    for (month in 1..12) {
        // 2で余りが0なら偶数
        if (month % 2 == 0) {
            println("偶数月${month}")
        }
    }
}