package codingchallenge16

fun main() {

}

fun sumRomanNumerals(romanNumeral1: String, romanNumeral2: String): String {

    val substitutedNumeral1 = substituteSubtractives(romanNumeral1)
    val substitutedNumeral2 = substituteSubtractives(romanNumeral2)

    val catenatedNumeral = substitutedNumeral1 + substitutedNumeral2

    val sortedNumeral = sortRomanNumeral(catenatedNumeral)
    val compressedNumeral = compressRomanNumerals(sortedNumeral)
    return addSubtractives(compressedNumeral)

}

fun addSubtractives(romanNumeral: String): String {
    return romanNumeral.replace("CCCC","CD").replace("LXXXX", "XC").replace("XXXX","XL").replace("VIIII","IX")
        .replace("IIII","IV")
}

fun compressRomanNumerals(romanNumeral: String): String {

    return romanNumeral.replace("IIIII", "V").replace("VV", "X").replace("XXXXX", "L").replace("LL", "C")
        .replace("CCCCC", "D").replace("DD", "M")
}

fun substituteSubtractives(romanNumeral: String): String {
    //"I" can be subtracted from "V" and "X" only. "X" can be subtracted from "L" and "C" only.
    // "C" can be subtracted from "D" and "M" only. "V", "L", and "D" can never be subtracted.
    return romanNumeral.replace("IV", "IIII").replace("IX", "VIIII").replace("XC", "LXXXX").replace("XL", "XXXX")
        .replace("CD", "CCCC").replace("CM", "DCCCC")
}

fun sortRomanNumeral(romanNumeral: String): String {
    val romanNumeralComparator = RomanNumeralComparator()
    return romanNumeral.toList().sortedWith(romanNumeralComparator).joinToString().replace(",", "").replace(" ", "")
}

class RomanNumeralComparator : Comparator<Char> {
    override fun compare(c1: Char, c2: Char): Int {
        //Sort order - M, D, C, L, X, V, I
        val sortedNumerals = "MDCLXVI"
        val c1Value = sortedNumerals.indexOf(c1)
        val c2Value = sortedNumerals.indexOf(c2)

        if (c1Value > c2Value)
            return 1
        if (c2Value > c1Value)
            return -1
        return 0
    }
}


