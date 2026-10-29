package io.github.kd1363656.basics.lesson07

// 第7章：クラスとオブジェクト
fun main() {
//    showClassDefinition     ()
//    showInitBlock           ()
//    showSecondaryConstructor()
//    showComputedProperty    ()
//    showCustomSetter        ()
//    showDataClass           ()
//    showObjectDeclaration   ()
//    showCompanionObject     ()
//    showObjectExpression    ()
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
        
    }
}