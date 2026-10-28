package io.github.kd1363656.basics.lesson05

fun loadNote() : String? = "牛乳を買う"

// 例のためのクラス(class は 第8章で学ぶ。ここでは「値をまとめて持つ入れ物」と考えてください)
class Profile(val name    : String?)
class User(val profile : Profile?)
class Task(val id : Long, val title : String)

fun findTask(id : Long) : Task? = if(id == 1L) Task(1, "掃除") else null

// return や throw と組み合わせて「早期リターンにもよく使う」
fun showTask(id : Long){
    val task = findTask(id) ?: return // 見つからなければ何もしない
    println(task.title)               // ここではtaskは非null
}

fun findTitle(id : Long) : String?
{
    return null
}

class Speaker
{
    lateinit var engineName : String // 後で代入する

    val voices : List<String> by lazy{ // 最初に使われたときに一度だけ計算
        println("音声一覧を読み込み中...")
        listOf ("標準", "高め")
    }
}

fun main()
{
//    var memo : String = "牛乳を買う"
//    // memo = null // エラー : Stringには null を入れられない
//
//    var note : String? = null // String? は null を入れられる
//    note = "後で書く"
//
//    val memoNote : String? = loadNote()
//    // println(note.length) // エラー : noteは null かもしれない
//
//    if (memoNote != null) {
//        println(note.length) // ここでは note はStringとして扱われる
//    }
//
//    val note : String? = null
//    val user : User?   = User(Profile("Reimu"))
//
//    val length : Int? = note?.length             // note が null なら length も null
//    val upper = user?.profile?.name?.uppercase() // つなげて書ける
//
//    println(length) // null
//    println(upper)  // REIMU
//
//    val note     : String? = null
//    val memoText : String  = note ?: "メモなし"
//    val length   : Int     = note?.length ?: 0
//    println(memoText) // メモなし
//    println(length)
//
//    showTask(1) // 掃除
//    showTask(2) // 何も表示されない
//
//    // 非nullアサーション
//    // Kotlinがnullかどうかをチェックしてくれるのにわざわざ使う必要性は薄いと考える
//    val note   : String? = "牛乳を買う"
//    val length = note!!.length // noteがnullならNullPointerException
//    println(length)            // 5
//
//
//    val note : String? = "牛乳を買う"
//
//    note?.let { text ->
//        println("メモ : $text") // note が null でなければ実行(text は String)
//    }
//
//    // 引数名を省略すると it になる
//    note.let{ "メモ : $it" }
//
//    val speaker = Speaker()
//
//    speaker.engineName = "標準エンジン"
//    println(speaker.engineName)
//    println(speaker.voices) // ここで初めて「音声一覧を読み込み中」
//    println(speaker.voices) // 2回目は計算しない

    findTitle(0) ?: println("(削除されたタスク)")



}