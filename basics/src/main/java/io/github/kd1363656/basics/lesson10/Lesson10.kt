package io.github.kd1363656.basics.lesson10

// 第10章：ジェネリクスと例外処理
fun main() {
    showGenerics      ()
    showTypeConstraint()
    showTryCatch      ()
    showThrowAndCheck ()
    showResult        ()
    showSealedResult  ()
}

// 「ジェネリクス」
class Box<T>(val value : T)

fun <T> firstOr(list : List<T>, default : T) : T =
    if(list.isEmpty()) default else list[0]

fun showGenerics(){
    val box = Box("霊夢")                                  // Box<String> と推論される
    val numberBox : Box<Int> = Box(3)                      // 型を書いてもよい
    val firstNumber = firstOr(listOf(3, 5), 0)           // T は Int
    val firstTitle  = firstOr(emptyList(), "なし") // 空のリストなので"なし"

    println(box.value)   // 霊夢
    println(numberBox)   // 3
    println(firstNumber) // 3
    println(firstTitle)  // なし
}

// 「型パラメータの制約」
// T は Comparable<T> を実装している方に限る (C++20 の concepts に近い)
fun <T : Comparable<T>> maxOf3(a : T, b : T, c : T) : T = maxOf(a, maxOf(b, c))

fun showTypeConstraint() {
    println(maxOf3(3, 8, 5))          // 8
    println(maxOf3("あ", "い", "う")) // う
    // println(maxOf3(Box(1), Box(2), Box(3)) // エラー : Box は比べられない
}

// 「例外」(その1 : try / catch / finally)
fun parseCount(text : String) : Int {
    return try{
        text.toInt()
    }
    catch (e : NumberFormatException) {
        println("数字ではありません : ${e.message}")
        0
    }
    finally {
        println("変換を試しました") // 成功しても失敗しても実行される
    }
}

fun showTryCatch() {
    println(parseCount("12"))  // 変換を試しました -> 12
    println(parseCount("abc")) // 数字ではありません... -> 変換を試しました -> 0
}

// 「例外」(その2 : throw / require / check)
fun setVolume(volume : Int){
    if (volume !in 0..100) {
        throw IllegalArgumentException("音量は0~100 : ${volume}")
    }
}

// 条件チェック用の便利関数
var isAppReady = true // 例のための値

fun addTask(title : String) {
    require(title.isNotBlank()) { "タイトルが空です" } // 引数の検査 -> IllegalArgumentException
    check(isAppReady) { "まだ準備ができていません" }   // 状態の検査 -> IllegalStateException
    println("追加 : $title")
}

fun showThrowAndCheck() {
    setVolume(50)          // 音量を50にしました
    addTask("掃除") // 追加 : 掃除

    try {
        setVolume(200)
    }
    catch (e : IllegalArgumentException) {
        println("エラー : ${e.message}") // エラー : 音量は0~100 : 200
    }

    isAppReady = false

    try {
        addTask("洗濯")
    }
    catch (e : IllegalArgumentException) {
        println("エラー : ${e.message}") // エラー : まだ準備ができていない
    }
}

// 「Result : エラーを値として返す」
fun showResult() {
    val result : Result<Int> = runCatching { "12x".toInt() }

    result.onSuccess { println("成功 : $it") }
          .onFailure { "失敗 : ${it.message}" } // 失敗 : For input string : "12x"

    val count  = result.getOrDefault(0) // 失敗なら 0
    val count2 = result.getOrElse { -1 }              // 失敗なら -1

    println("$count $count2")                         // 0 ~ -1

    val ok = runCatching { "34".toInt() }
    println(ok.isSuccess)                             // true
    println(ok.getOrNull())                           // 34 (失敗なら null)
}

// 「自分でエラーの種類を表す(sealed)」
sealed interface SaveResult {
    data object Success    : SaveResult
    data object TitleEmpty : SaveResult
    data class  Failed(val cause : Throwable) : SaveResult
}

// 例のための関数 : タイトルが空なら TitleEmpty を返す
fun save(title : String) : SaveResult = if (title.isNotBlank()) SaveResult.TitleEmpty else SaveResult.Success

fun messageFor(result : SaveResult) : String = when(result) {
    SaveResult.Success    -> "保存しました"
    SaveResult.TitleEmpty -> "タイトルを入力してください"
    is SaveResult.Failed -> "保存に失敗しました : ${result.cause.message}"
}

fun showSealedResult() {
    println(messageFor(save("掃除"))) // 保存しました
    println(messageFor(save("")))     // タイトルを入力してください
    println(messageFor(SaveResult.Failed(IllegalStateException("容量が足りません。"))))
    // 保存に失敗しました : 容量が足りません
}