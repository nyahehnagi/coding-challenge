package codingchallenge16

fun main() {

    while (shallWeDoAnotherOne()) {
        consoleInputAndAnswer()
    }
}

data class RomanNumeral(val romanNumeral: String) {

    operator fun plus(other: RomanNumeral) =
        RomanNumeral(this.removeSubtractives().toString() + other.removeSubtractives().toString())
            .sortRomanNumeral()
            .compressRomanNumeral()
            .addSubtractives()

    override fun toString(): String = romanNumeral

    fun removeSubtractives(): RomanNumeral =
        RomanNumeral(
            romanNumeral.replace("IV", "IIII").replace("IX", "VIIII").replace("XC", "LXXXX").replace("XL", "XXXX")
                .replace("CD", "CCCC").replace("CM", "DCCCC")
        )

    fun sortRomanNumeral(): RomanNumeral {
        val romanNumeralComparator = RomanNumeralComparator()
        return RomanNumeral(romanNumeral.toList().sortedWith(romanNumeralComparator).joinToString(""))
    }

    fun compressRomanNumeral(): RomanNumeral =
        RomanNumeral(
            romanNumeral.replace("IIIII", "V").replace("VV", "X").replace("XXXXX", "L").replace("LL", "C")
                .replace("CCCCC", "D").replace("DD", "M")
        )

    fun addSubtractives(): RomanNumeral =
        RomanNumeral(
            romanNumeral.replace("CCCC", "CD").replace("LXXXX", "XC").replace("XXXX", "XL").replace("VIIII", "IX")
                .replace("IIII", "IV")
        )
}

class RomanNumeralComparator : Comparator<Char> {
    override fun compare(c1: Char, c2: Char): Int {
        //Sort order - M, D, C, L, X, V, I
        val sortedNumerals = "MDCLXVI"
        val c1Value = sortedNumerals.indexOf(c1)
        val c2Value = sortedNumerals.indexOf(c2)

        return when {
            c1Value > c2Value -> 1
            c2Value > c1Value -> -1
            else -> 0
        }
    }
}

fun shallWeDoAnotherOne(): Boolean {
    println("Do you want to add some Roman Numerals? (Y/N): ")
    val userResponse = readLine()!!
    return when {
        userResponse.toUpperCase() == "Y" -> true
        userResponse.toUpperCase() == "N" -> false
        else -> false
    }
}

fun consoleInputAndAnswer() {
    println("Enter first Roman numeral: ")
    val romanNumeral1 = RomanNumeral(readLine()!!)
    println("Enter second Roman numeral: ")
    val romanNumeral2 = RomanNumeral(readLine()!!)
    val result = romanNumeral1 + romanNumeral2
    println("$romanNumeral1 + $romanNumeral2 is: ${result}")
}