package codingchallenge14

fun main() {

    val integerList = listOf(1, 2, 3, 4)
    val foldedString = myFold(integerList, "") { acc, element -> acc + element.toString() }

    println(foldedString)
}

fun myFold(intList: List<Int>, accString: String, funcString: (s: String, element: Int) -> String): String {
    var accum = accString
    intList.map { accum = funcString(accum, it) }
    return accum
}

fun <A, T> myGenericFold(anyList: List<T>, accAny: A, funcAny: (acc: A, element: T) -> A): A {
    var accum = accAny
    anyList.map { accum = funcAny(accum, it) }
    return accum
}
