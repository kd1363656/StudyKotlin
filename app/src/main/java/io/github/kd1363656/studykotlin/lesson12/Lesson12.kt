package io.github.kd1363656.studykotlin.lesson12

import android.content.Context
import java.io.File
import java.time.LocalDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encodeToString
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream

// 第12章：JSON の読み書き
// MainActivity から呼ぶ、この章の入口。println の結果は Logcat に表示される
suspend fun runLesson12(context: Context) {
    showEncodeAndDecode()
    showCustomJson     ()
    showSerialName     ()
    showNestedJson     ()
    showSealedJson     ()
    showLocalDateJson  ()
    showAssetsJson     (context)
    showSaveBackup     (context)
}

// 「基本 : data class ~ JSON」
@Serializable // これをつけるだけで変換できるようになる
data class TaskJson(
    val id : Long,
    val title : String,
    val isDone : Boolean = false,  // JSONになければデフォルト値が使われる
    val memo : String? = null,)

fun showEncodeAndDecode(){
    val task = TaskJson(1, "削除")

    val text : String = Json.encodeToString(task) // シリアライズ
    println(text) // {"id":1,"title":"掃除"} <- デフォルト値のままの項目は省略される

    val back : TaskJson = Json.decodeFromString(text) // デシリアライズ
    println(back == task) // true

    val list : List<TaskJson> = Json.decodeFromString("""[{"id": 1,"title":"a"}, {"id":2, "title":"b","isDone":true}]""")
    println(list)  // [TaskJson(id=1, title=a, isDone=false, memo=null), TaskJson(id=2, title=b, isDone=true, memo=null)]
}

// 「設定を変えた Json を作る」
val AppJson = Json {
    ignoreUnknownKeys = true // 知らないキーがあっても無視(古いアプリで新しいJsonを読むとき安全)
    encodeDefaults    = true // デフォルト値の項目も書き出す
    prettyPrint       = true // 改行とインデントを入れて読みやすく
}

fun showCustomJson() {
    println(AppJson.encodeToString(TaskJson(2, "洗濯")))
    // {
    //     "id": 2,
    //     "title": "洗濯",
    //     "isDone": false,
    //     "memo": null
    // }

    val newer = """{"id" : 3, "title" : "買い物", "priority" : 5}""" // TaskJsonにない priority がある
    println(AppJson.decodeFromString<TaskJson>(newer).title)         // 買い物
    // Json.decodeFromString<TaskJson>(newer)                        // 普通の Json だと例外になる
}

// 「名前を変える・含めない」
@Serializable
data class CharacterJson(
    @SerialName("character_id") val id : String, // JSON側のキー名を指定
    val name : String,
    @Transient val cache : String = ""           // JSONに含めない(デフォルト値が必須)
)

fun showSerialName() {
    val reimu = CharacterJson("reimu", "博麗霊夢", cache = "一時的なデータ")
    println(Json.encodeToString(reimu))
}

// 「入れ子・enum・sealed・日付」(その1 : 入れ子と enum)
@Serializable
enum class TimeOfDay {
    @SerialName("morning")   MORNING, // JSON では小文字の "morning" と書く
    @SerialName("afternoon") AFTERNOON,
    @SerialName("evening")   EVENING,
}

@Serializable
data class Dialogues(
    val version     : Int,
    val characterId : String,
    val greetings   : Map<TimeOfDay, List<String>>, // Mapのキーはenumや文字列にできる
    val allDone     : String,
)

fun showNestedJson() {
    val text = """
        {
            "version" : 1,
            "characterId" : "reimu",
            "greetings" : { "morning" : ["おはよう"], "evening" : ["おつかれさま"] },
            "allDone" : "お茶にしましょ"
        
        }
    """

    val dialogues = AppJson.decodeFromString<Dialogues>(text)

    println(dialogues.greetings[TimeOfDay.MORNING])   // おはよう
    println(dialogues.greetings[TimeOfDay.AFTERNOON]) // null (JSON にないので)
}

// 「入れ子・enum・sealed・日付」(その2 : sealed)
@Serializable
sealed interface  Line {
    @Serializable @SerialName("text")
    data class Text(val value : String) : Line
    @Serializable @SerialName("taskCount")
    data class TaskCount(val prefix : String, val suffix : String) : Line
}

fun showSealedJson() {
    val lines : List<Line> = listOf(Line.Text("おはよう"), Line.TaskCount("今日は", "件よ"))
    val text = Json.encodeToString(lines)

    println(text)
    // [{"type":"text","value":"おはよう"},{"type":"taskCount","prefix":"今日は","suffix":"件よ"}]

    val back = Json.decodeFromString<List<Line>>(text)
    println(back[1] is Line.TaskCount) // true ("type をみて正しい種類に戻る)
}

// 「入れ子・enum・sealed・日付」(その3 : 日付)
object LocalDateSerializer : KSerializer<LocalDate> {
    override val descriptor = PrimitiveSerialDescriptor("LocalDate", PrimitiveKind.STRING)
    override fun serialize  (encoder: Encoder, value : LocalDate) = encoder.encodeString(value.toString())
    override fun deserialize(decoder : Decoder) : LocalDate = LocalDate.parse(decoder.decodeString())
}

@Serializable
data class DayJson(
    @Serializable(with = LocalDateSerializer::class) val date : LocalDate,
    val tasks : List<TaskJson>,
)

fun showLocalDateJson() {
    val day = DayJson(LocalDate.of(2026, 10, 27), listOf(TaskJson(1, "掃除")))
    val text = Json.encodeToString(day)
    println(text) //{"date":"2026-10-27","tasks":[{"id":1,"title":"掃除"}]}
    println(Json.decodeFromString<DayJson>(text).date.dayOfWeek) // TUESDAY
}