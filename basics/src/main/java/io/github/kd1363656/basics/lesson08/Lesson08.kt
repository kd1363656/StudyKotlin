package io.github.kd1363656.basics.lesson08

// 第8章：継承・インターフェース・enum・sealed
fun main() {
    showInheritance    ()
    showAbstractClass  ()
    showInterface      ()
    showEnumClass      ()
    showSealedInterface()

    // ここから問題
    val reader =  Reader()

    val list : List<String> = listOf("おはよう", "元気")

    reader.read(list)
}

open class Enemy {
    open fun damage() : Int = 1
}

class Boss : Enemy() {
    override fun damage() : Int = super.damage() * 10
}

fun showInheritance() {
    val enemies : List<Enemy> = listOf(Enemy(), Boss())

    for (enemy in enemies) {
        println(enemy.damage()) // 1、10
    }
}

// 「抽象クラス」
abstract class Youkai(val name : String) {
    abstract fun skill() : String // 中身は子クラスで書く(純粋仮想関数)

    fun introduce() { // 普通の関数も持てる
        println("${name}の得意技 : ${skill()}")
    }
}

class Fairy(name : String) : Youkai(name) {
    override fun skill() : String = "いたずら"
}

fun showAbstractClass() {
    val cirno = Fairy("チルノ")
    cirno.introduce()                // チルノの得意技 : いたずら
    // val unknown = Youkai("誰か")  // エラー : 抽象クラスからは作れない
}

// 「インターフェース」
interface Speaker {
    fun speak(text : String)
    fun stop() {} // ドフォルト実装も書ける
}

class AndroidTtsSpeaker : Speaker {
    override fun speak(text : String) {
        // Androidの音声読み上げを使う(第18章)。ここではまだ何もしない
    }
}

class LogSpeaker : Speaker {
    override fun speak(text : String) {
        println("[読み上げ] $text")
    }
}

fun morningCall(speaker : Speaker) {
    speaker.speak(("おはよう! 教のタスクは3件だよ"))
    speaker.stop()
}

fun showInterface() {
    morningCall(LogSpeaker())        // [読み上げ] おはよう!今日のタスクは3件だよ
    morningCall(AndroidTtsSpeaker()) // 何も表示されない(中身がまだ空のため)
}

// 「enum class」
enum class Expression(val fileSuffix : String) {
    NORMAL("normal"),
    SMILE ("smile"),
    BLUSH ("blush");

    fun isHappy() : Boolean = this == SMILE ||
                              this == BLUSH
}

fun showEnumClass() {
    val face = Expression.SMILE

    println(face)               // SMILE
    println(face.fileSuffix)    // smile
    println(face.isHappy())     // true
    println(Expression.entries) // 全種類の一覧「NORMAL, SMILE, BLUSH」

    val label = when(face) { // 全種類を書けば else は不要
        Expression.NORMAL -> "通常"
        Expression.SMILE  -> "笑顔"
        Expression.BLUSH  -> "赤面"
    }

    println(label) // 笑顔
}

// 「sealed interface / sealed class」
sealed interface LoadState {
    data object Loding : LoadState                              // データなし
    data class  Success(val tasks   : List<String>) : LoadState // 一覧を持つ
    data class  Error  (val message : String)       : LoadState // エラーメッセージを持つ
}

fun render(state : LoadState) : String = when(state) {
    LoadState.Loding     -> "読み込み中"
    is LoadState.Success -> "${state.tasks.size}件" // スマートキャストで tasks が使える
    is LoadState.Error   -> "エラー : ${state.message}"
} // 全朱里を書いたので else は不要。種類を追加すると、ここがコンパイルエラーになる

fun showSealedInterface() {
    println(render(LoadState.Loding))                                  // 読み込み中...
    println(render(LoadState.Success(listOf("掃除", "洗濯")))) // 2件
    println(render(LoadState.Error("通信できません")))                 // エラー : 通信できません
}

enum class Tab(val fileSuffix : String)
{
    TODAY   ("today"),
    AYear   ("aYear"),
    SETTINGS("Settings");
}

interface TaskReader
{
    fun read(titles : List<String>)
}

class Reader : TaskReader{
    override fun read(titles : List<String>) {
        println(titles)
    }
}