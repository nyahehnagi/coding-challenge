package codingchallenge16

import org.hamcrest.CoreMatchers.equalTo
import org.junit.Test
import org.hamcrest.MatcherAssert.assertThat

class RomanNumeralTest {

    @Test
    fun `Should test that 2 Roman numeral strings are summed together`() {
        val romanString1 = "X"
        val romanString2 = "X"
        val sumOfRomanNumerals = sumRomanNumerals(romanString1, romanString2)

        assertThat(sumOfRomanNumerals, equalTo("XX"))
    }

    @Test
    fun `Should test that a roman numeral subtractives are substituted - one subtractive substitution`() {
        val romanString1 = "IX"
        val substitutedSubtractives = substituteSubtractives(romanString1)

        assertThat(substitutedSubtractives, equalTo("VIIII"))
    }

    @Test
    fun `Should test that a roman numeral subtractives are substituted - 3 subtractive substitutions `() {
        val romanString1 = "MCMXCIV"
        val substitutedSubtractives = substituteSubtractives(romanString1)

        assertThat(substitutedSubtractives, equalTo("MDCCCCLXXXXIIII"))
    }

    @Test
    fun `Should test sorting a string of roman numerals into the correct order of precedence`() {
        val romanString1 = "MDCCCCLXXXXIIIIMDCCCCLXXXXIIII"
        val sortedRomanNumeral = sortRomanNumeral(romanString1)

        assertThat(sortedRomanNumeral, equalTo("MMDDCCCCCCCCLLXXXXXXXXIIIIIIII"))
    }

    @Test

    fun `Should test that a roman numeral is combined to highlest level numerals possible`() {
        val romanString1 = "DCCCCCCLXXXXXVVIIII"
        val compressedRomanNumeral = compressRomanNumeral(romanString1)

        assertThat(compressedRomanNumeral, equalTo("MCCXIIII"))
    }

    @Test
    fun `Should test the addition of subtractive elements of a roman numeral`() {
        val romanString1 = "MCCXIIII"
        val subtractiveAddedRomanNumeral = addSubtractives(romanString1)

        assertThat(subtractiveAddedRomanNumeral, equalTo("MCCXIV"))
    }
}