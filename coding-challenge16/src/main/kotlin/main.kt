package codingchallenge16

fun main() {
    print("Enter first Roman numeral: ")
    val romanNumeral1 :String = readLine()!!
    print("Enter second Roman numeral: ")
    val romanNumeral2 : String = readLine()!!
    val result = sumRomanNumerals(romanNumeral1,romanNumeral2)
    print("$romanNumeral1 + $romanNumeral2 is: $result")
}

fun sumRomanNumerals(romanNumeral1: String, romanNumeral2: String): String =
    addSubtractives(
        compressRomanNumeral(
            sortRomanNumeral(
                removeSubtractives(romanNumeral1) + removeSubtractives(romanNumeral2)
            )
        )
    )

fun addSubtractives(romanNumeral: String): String =
    romanNumeral.replace("CCCC", "CD").replace("LXXXX", "XC").replace("XXXX", "XL").replace("VIIII", "IX")
        .replace("IIII", "IV")

fun compressRomanNumeral(romanNumeral: String): String =
    romanNumeral.replace("IIIII", "V").replace("VV", "X").replace("XXXXX", "L").replace("LL", "C")
        .replace("CCCCC", "D").replace("DD", "M")

fun removeSubtractives(romanNumeral: String): String =
    romanNumeral.replace("IV", "IIII").replace("IX", "VIIII").replace("XC", "LXXXX").replace("XL", "XXXX")
        .replace("CD", "CCCC").replace("CM", "DCCCC")

fun sortRomanNumeral(romanNumeral: String): String {
    val romanNumeralComparator = RomanNumeralComparator()
    return romanNumeral.toList().sortedWith(romanNumeralComparator).joinToString("")
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


