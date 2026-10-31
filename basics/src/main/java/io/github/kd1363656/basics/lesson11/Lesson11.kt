package io.github.kd1363656.basics.lesson11

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

// 第11章：コルーチンと Flow
fun main() {
    showSuspendFunction()
    showLaunchAndAsync ()
    showDispatchers    ()
    showFlow           ()
    showStateFlow      ()
    showCombine        ()

    // ここから問題
    runBlocking {
        exerciseOne().map { "3件中${it}件目です" }
                     .collect { println(it) }
    }
}

// 「suspend 関数」
// 例のための関数 (本物のアプリでは、ファイルやDBから読み込む)
suspend fun loadTasks() : List<String> {
    delay(1000) // 1秒待つ(スレッドは止めない,、Thread.sleep とは違う)
    return listOf("掃除", "洗濯")
}

fun showSuspendFunction() {
    runBlocking {                // 練習用 : 中のコルーチンが終わるまで待つ
        println("読み込み開始")
        val tasks = loadTasks() // ここで一時停止 -> 1秒後に再開
        println("読み込み完了 : ${tasks}")
    }
}

// 「launch と async」
suspend fun  loadVoicesSettings() : String {
    delay(1000)
    return "速さ 1.0"
}

fun showLaunchAndAsync() {
    runBlocking {
        // launch : 結果を返さない仕事を始める
        val job = launch {
            delay(5000)
            println("通知を予約しました")
        }

        // async : 結果を返す仕事をはじめ,awaitで受け取る
        val tasks = async { loadTasks() }
        val voice = async { loadVoicesSettings() }

        println("${tasks.await()} / ${voice.await()}") // 2つを同時に進める (合わせて約1秒)

        job.cancel() // 途中でやめることもできる (ここではもう終わっている)
    }
}

// 「Dispatchers : どのスレッドで動かすか」
suspend fun readFile(path : String) : String = withContext(Dispatchers.IO) {
    java.io.File(path).readText() // IO用のスレッドで実行され、終わったら元に戻る
}

fun showDispatchers() {
    runBlocking {
        val file = java.io.File("memo.txt")
        file.writeText("牛乳を買う")          // 例のためのファイルを作る
        println(readFile("memo.txt")) // 牛乳を買う
        file.delete()                        // 例のためのファイルを消す
    }
}

// 「Flow : 時間とともに流れてくる値」
fun countdown() : Flow<Int> = flow{
    for (i in 3 downTo 1){
        emit(i)          // 値を返す
        delay(1000)
    }
}

fun showFlow(){
    runBlocking {
        countdown().map { "あと${it}秒" }   // コレクションと同じ操作ができる
                   .collect { println(it) } // 流れてきた値を受け取る (collect するまで動かない)
    }
}

// 「StateFlow : 「今の値」を持つFlow」
fun showStateFlow() {
    val count = MutableStateFlow(0) // 初期値
    count.value = 1         //
    count.update { it + 1 } // 値を更新
    println(count.value)

    val readOnly : StateFlow<Int> = count.asStateFlow() // 外に読み取り専用で渡す

    println(readOnly.value) // 2
    // readOnly.value = 5   // エラー : StateFlow の value は書き換えられない
}

// 「Flowを組み合わせる」
// 例のためのクラス
data class Task(val title : String)
data class Character(val name : String)

fun showCombine() {
    runBlocking {
        // 本物のアプリでは、tasksFlow は DB から、characterFlow は設定から受け取る
        val tasksFlow : Flow<List<Task>> = flowOf(listOf(Task("掃除"), Task("洗濯")))
        val characterFlow : Flow<Character> = flowOf(Character("霊夢"))

        // どちらかが変わるたびに、両方の最新値から新しい値を作る
        val screen : Flow<String> = combine(tasksFlow, characterFlow) { tasks, chara ->
            "${chara.name} : 今日は${tasks.size}件"
        }
        screen.collect { println(it) } // 霊夢 : 今日は2件
    }
}

fun exerciseOne() : Flow<Int> = flow{
    for (i in 1 ..3){
        emit(i)
        delay(1000)
    }
}