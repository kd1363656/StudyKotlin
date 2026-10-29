package io.github.kd1363656.basics.lesson04

// 第4章 : 関数
fun main() {
    showFunctionDefinition      ()
    showUnitFunction            ()
    showSingleExpression        ()
    showDefaultAndNamedArguments()
    showTopLevelFunction        ()
    showVararg                  ()
    showLocalFunction           ()
    showNothingType             ()

    // ここから問題
    var result = progressRate(4, 5)

    notify(title = "Zero", silent = true)
}

// 「関数の定義」
fun remaining(total : Int, done : Int) : Int {
    return total - done
}

fun showFunctionDefinition() {
    println(remaining(5, 2))    // 3

    val left = remaining(10, 7) // 戻り値を変数で受け取る

    println("残り${left}件")                   // 残り3件
}

// 「戻り値がない関数 (Unit) 」
fun greet(name : String) { // : Unit は省略
    println("おはよう、$name")
}

fun greetWithUnit(name : String) : Unit { // 書いてもよいが、普通は書かない
    println("こんにちは、$name")
}

fun showUnitFunction() {
    greet("霊夢")           // おはよう、霊夢
    greetWithUnit("魔理沙") // こんにちは、魔理沙
}

// 「単一式関数」
// 上のremaining と同じ処理を1行で書いたもの (名前がぶつからないように Short を付けた)
fun remainingShort(total : Int, done : Int) = total - done
fun isAllDone(total : Int, done : Int) : Boolean = total > 0 && total == done
fun showSingleExpression() {
    println(remainingShort(5, 2)) // 3
    println(isAllDone     (5, 5)) // true
    println(isAllDone     (0, 0)) // false (タスクが0件の時は「全部終わった」としない)
}

// 「デフォルト引数と名前付き引数」
fun speak(text  : String,
          pitch : Float   = 1.0F,
          speed : Float   = 1.0F,
          queue : Boolean = false)
{
    println("[$text] pitch = $pitch speed = $speed queue = $queue")
}

fun showDefaultAndNamedArguments() {
    speak("おはよう")               // 残りはデフォルト引数
    speak("おはよう", speed = 1.2F) // speed だけ指定
    speak(text = "がんばれ", queue = true) // 順番を気にせず指定できる
    speak("またね", 0.8F)   // 名前なしなら、前から順に pitch に入る
}

// 「トップレベル関数と可視性」
fun greetingFor(hour : Int) : String = when(hour) {
    in 5..10  -> "おはよう"
    in 11..17 -> "こんにちは"
    else             -> "こんばんは"
}

private fun formatHour(hour : Int) : String = "${hour}時" // このファイルの中だけで使える

fun showTopLevelFunction() {
    println(greetingFor(9))                           // おはよう
    println("${formatHour(20)} : ${greetingFor(20)}") // 20時 : こんばんは
}

// 「可変長引数 (vararg) 」の前
fun addAll(vararg titles : String) {
    println("${titles.size}件を追加")
    for (title in titles) {
        println("追加 : $title")
    }
}

fun showVararg() {
    addAll("掃除", "洗濯", "買い物") // 3件を追加
    addAll("燭台")                   // 1件を追加
}

// 「ローカル関数」
fun buildMessage(done : Int, total : Int) : String  {
    fun percent() = done * 100 / total // 関数の中の関数

    return "達成率 ${percent()}%"
}

fun showLocalFunction() {
    println(buildMessage(3, 4)) // 達成率 75%
}

// 「Nothing型」
fun loadTaskFromCloud() : Int = TODO("後で実装する") // 呼ぶと NotImplementedError

fun showNothingType() {
    println("loadTasksFromCloud はまだ呼ばない")
    // loadTasksFromCloud() // // を外して実行する
}

fun progressRate(finishedCount : Int,    allCount : Int ) : Float = if (allCount == 0) 0.0F else finishedCount.toFloat() / allCount.toFloat()
fun notify      (title         : String, body     : String = "", silent : Boolean = false)
{
    println("Title : ${title}, Body : ${body}, Silent : ${silent}")
}