package io.github.kd1363656.basics.lesson07

// 第7章：クラスとオブジェクト
fun main() {
    showClassDefinition     ()
    showInitBlock           ()
    showSecondaryConstructor()
    showComputedProperty    ()
    showCustomSetter        ()
    showDataClass           ()
    showObjectDeclaration   ()
    showCompanionObject     ()
    showObjectExpression    ()

    // ここから問題
    val todoFirst  = Todo(0, "最高", false, "メモ")
    val todoSecond = Todo(1, "最悪", false, "メモ")

    todoFirst.copy(isFinished = true)

    val daily = DailyProgress(3, 5)

    println("残り数 : ${daily.remaining}")
}

// 「クラスの定義」
class Character(val name : String, level : Int){
    var level = level

    private set // 書き換えはクラスの中だけ

    fun levelUp() {
        level++
    }
}

fun showClassDefinition() {
    val reimu = Character("霊夢", 1) // new は不要
    reimu.levelUp()
    println("${reimu.name} Lv.${reimu.level}") // 霊夢 Lv.2

    // reimu.level = 99 // エラー : setterがprivate
}

// 「init ブロック」
class Task(val title : String) {
    init {
        require(title.isNotBlank()) { "タイトルが空です" } // 条件を満たさなければ例外

        println("Task を作成 : $title")
    }
}

fun showInitBlock() {
    val task = Task("掃除") // Task を作成 : 掃除
    println(task.title)            // 掃除
    // Task("")                    // 実行すると例外 : タイトルが空です
}

// 「セカンダリコンストラクタ」
class StudyTime(val totalMinutes : Int) {
    // 「時間と分」でも作れるようにする。 : this(...) でプライマリコンストラクタに渡す
    constructor(hours : Int, minutes : Int) : this(hours * 60 * minutes)
}

fun showSecondaryConstructor() {

    val short = StudyTime(45)         // プライマリコンストラクタ
    val long  = StudyTime(1, 30) // セカンダリコンストラクタ

    println(short.totalMinutes)                     // 45
    println(long.totalMinutes)                      // 90
}

// 「プロパティ」(その1 : get)
class Progress(val done : Int, val total : Int) {
    // 計算で値を返すプロパティ(値は保持されず、読むたびに計算される)
    val rate : Float
        get() = if (total == 0) 0F else done.toFloat() / total

    val isComplete : Boolean
        get() = total > 0 &&
                done == total
}

fun showComputedProperty() {
    val progress = Progress(3,4)

    println(progress.rate)       // 0.75
    println(progress.isComplete) //false
    println(Progress(0, 0).rate) // 0.0(0で割らないようにしてある)
}

// 「プロパティ」(その2 : set と field)
class Settings {
    var speechSpeed : Float = 1.0F
        set(value) {
            field = value.coerceIn(0.5F, 2.0F) // field はに保存される値
        }
}

fun showCustomSetter() {
    val settings = Settings()

    println(settings.speechSpeed) // 1.0F (最初の値)
    settings.speechSpeed = 5.0F   // ここで set(5.0F) が呼ばれる
    println(settings.speechSpeed) // 2.0(0.5F ~ 2.0F の範囲に収められる)
    settings.speechSpeed = 1.5F
    println(settings.speechSpeed) // 1.5F (範囲内なのでそのまま)
}

// 「data class」
data class TodoItem (
    val id     : Long,
    val title  : String,
    val isDone : Boolean = false,
)

fun showDataClass() {
    val first  = TodoItem(1, "掃除")
    val second = TodoItem(1, "掃除")

    println(first == second) // true(中身で比較)
    println(first)           // TodoItem(id = 1, title = 掃除, isDone = false)

    val done = first.copy(isDone = true) // first はそのまま、isDone だけ違う新しい TodoItem
    println(first.isDone)                // false
    println(done.isDone)                 // true

    val (id, title) = done // 分解宣言
    println("$id $title")  // 1 掃除
}

// 「object 宣言(シングルトン)」
object Greetings {
    val morning = listOf("おはよう", "朝だよ")
    fun random() : String = morning.random()
}

fun showObjectDeclaration() {
    println(Greetings.random()) // おはよう か 朝だよ(実行するたびに変わる)
    println(Greetings.morning)  // [おはよう,朝だよ]
    //val other = Greetings()   // エラー : object からオブジェクトは作れない
}

// 「companion object」
class Memo(val title : String) {
    companion object {
        const val MAX_TITLE_LENGTH = 40

        fun untitled() : Memo = Memo("無題") // 「名前付きコンストラクタ」として使うことが多い
    }
}

fun showCompanionObject() {
    println(Memo.MAX_TITLE_LENGTH) // 40
    val memo = Memo.untitled()
    println(memo.title)            // 無題
}

// 「object 式」
fun showObjectExpression() {
    val listener = object : Runnable {
        override fun run() {
            println("実行")
        }
    }

    listener.run() // 実行
}

data class Todo(
    val id         : Long,
    val title      : String,
    val isFinished : Boolean,
    var memo       : String = ""
)

class DailyProgress(val finishedNum : Int, val totalNum : Int) {
    val remaining : Int
        get() = totalNum - finishedNum
}