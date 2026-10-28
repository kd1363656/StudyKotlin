package io.github.kd1363656.basics.lesson04

fun greet(name : String)
{
    println("おはよう, ${name}")
}

fun remaining(total : Int, done : Int) = total - done
fun isAllDone(total : Int, done : Int) : Boolean = total > 0 && total == done

// デフォルト引数
fun speak(text  : String,
          pitch : Float   = 1.0F,
          speed : Float   = 1.0F,
          queue : Boolean = false)
{
    println("「text」 pitch=${pitch} speed=${speed} queue=${queue}")
}

// 可変長引数
fun addAll(vararg titles : String)
{
    for (t in titles) println("追加 : $t")
}

// ローカル関数
fun buildMessage(done : Int, total : Int) : String
{
    fun percent() = done * 100 / total // 関数の中の関数

    return "達成率 ${percent()}%"
}

// Nothing型
fun notYet() : Int = TODO("あとで実装する") // 呼ぶとNotImplementedError

fun CalculateProgressPercent(currentAmount : Int, totalAmount : Int) : Float = if(totalAmount <= 0) 0.0F else currentAmount.toFloat() / totalAmount.toFloat()

fun notify(title : String,
           body  : String = "",
           silent : Boolean = false)
{
    println("notify : title=${title}, silent=${silent}")
}

fun main()
{
    greet("霊夢")

    println(remaining(5,2)) // 3
    println(isAllDone(5,5)) // true

    speak("おはよう")               // 残りはデフォルト値
    speak("おはよう", speed = 1.2F) // speed だけ指定
    speak("頑張れ",   queue = true) // 順番を気にせず指定できる

    addAll("掃除", "選択", "買い物")

    println(buildMessage(3, 4)) //  達成率 75%

    // ここから問題
    val progressPercent = CalculateProgressPercent(3, 4)

    println("進捗率 : ${progressPercent}")

    notify(title = "Title", silent = true)


}
