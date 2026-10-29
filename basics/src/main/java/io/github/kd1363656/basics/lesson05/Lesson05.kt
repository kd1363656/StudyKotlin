package io.github.kd1363656.basics.lesson05

import java.lang.module.FindException

// 第5章 : null安全
fun main(){
    showNullableType    ()
    showSmartCast       ()
    showSafeCall        ()
    showElvisBasics     ()
    showElvisWithReturn ()
    showNotNullAssertion()
    showLetForNullable  ()
    showLateinitAndLazy ()

    // ここから問題
    findTitleIfNull()

}

// 「null許容型」
// 例のための関数 : メモがあれば中身を、泣ければ null を返す
fun loadNote()      : String? = "牛乳を買う"
fun loadEmptyNote() : String? = null

fun showNullableType() {
    var memo : String = "牛乳を買う"
    // memo = null    // エラー : String には null を入れられない
    memo = "卵を買う" // null でない文字列なら入れられる

    var note : String? = null // String? は null を入れられる

    println(note) // null

    note = "あとで書く"

    println(note) // 後で書く

    val loaded : String? = loadNote()
    // println(loaded.lnegth) // エラー : loaded は null かもしれない
    println(memo + " / " + loaded)
}

// 「if でチェック -> スマートキャスト」
fun showSmartCast() {
    val note : String? = loadNote()
    if (note != null) {
        println(note.length) // 5 : ここでは note は String として扱われる
    }
    else {
        println("メモなし")
    }

    val empty : String? = loadEmptyNote()

    if (empty == null) {
        println("empty は null") // こちらが表示される
    }
}

// 「安全呼びだし ?.」
class Profile(val name : String?)
class User(val profile : Profile?)

fun showSafeCall() {
    val note : String? = null
    val user : User? = User(Profile("Reimu"))

    val length : Int? = note?.length             // note が null なら length も null
    val upper = user?.profile?.name?.uppercase() // つなげて書ける

    println(length) // null
    println(upper)  // REIMU

    val guest : User? = User(null) // プロフィールがないユーザー
    println(guest?.profile?.name)           // null (profile が null なので、そこで止まる)
}

// 「エルビス演算子 ?:」(その1 : 基本)
fun showElvisBasics() {
    val note : String? = null

    // 次の2行は同じ意味(どちらも"メモなし"になる)
    val withElvis = note ?: "メモなし"
    val withIf    = if (note != null) note else "メモなし"

    println(withElvis) // メモなし
    println(withIf)    // メモなし

    val hasNote : String? = "牛乳を買う"
    val noNote  : String? = null

    println(hasNote ?: "メモなし") // 牛乳を買う(null ではないので左側)
    println(noNote  ?: "メモなし") // メモなし  (null なので右側)
}


// 「エルビス演算子 ?:」(その2 : 早期リターン)
// 例のためのクラスと関数(class は第7章で学びます )
class Task(val id : Long, val title : String)

fun findTask(id : Long) : Task? = if (id == 1L) Task(1, "掃除") else null

// return や throw と組み合わせて「早期リターン」二もよく使う
fun printTaskTitle(id : Long) {
    val task = findTask(id) ?: return // 見つからなければ何もしない
    println(task.title)               // ここでは task は非null
}

fun showElvisWithReturn() {
    val note     : String? = null
    val memoText : String  = note ?: "メモなし"
    val length   : Int     = note?.length ?: 0

    println(memoText) // メモなし
    println(length)   // 0

    printTaskTitle(1) // 掃除なし
    printTaskTitle(2) // 何も表示されない
}

// 「非null アサーション!!」
// なるべく使うことを避ける
fun showNotNullAssertion() {
    val note : String? = "牛乳を買う"
    val length         = note!!.length // note が null なら NullPointerException
    println (length) // 5
}

// 「let でnull出ない時だけ処理する」
fun showLetForNullable() {
    val note : String? = "牛乳を買う"

    note?.let { text ->
        println("メモ : $text") // note が null でなければ実行(text は String)
    }

    // 引数名を省略すると it になる
    note?.let { println{"メモ : $it"} }

    val empty : String? = null

    empty?.let { println("ここは実行されない") }
}

// 「lateinit と遅延初期化」
// class は第7章で学ぶ。ここでは「プロパティを持つ入れ物」と考える
class Speaker {
    lateinit var entineName : String // 後で代入する

    val voices : List<String> by lazy { // 最初に使われたときに1度だけ計算
        println("音声一覧を読み込み中")
        listOf("標準", "高め")
    }
}

fun showLateinitAndLazy() {
    val speaker = Speaker()

    speaker.entineName = "標準エンジン"

    println(speaker.entineName)
    println(speaker.voices) // ここで初めて「音声一覧を読み込み中」
    println(speaker.voices) // 2回目は計算しない
}

fun findTitle(id : Long) : String? {
    if (id == 1L) {
        return "Title"
    }
    else {
        return null
    }
}

fun findTitleIfNull() {

    if (findTitle(0L) == null) {
        println("削除されたタスク");
    }
}