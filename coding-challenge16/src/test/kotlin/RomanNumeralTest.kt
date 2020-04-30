package codingchallenge16

import org.hamcrest.CoreMatchers.equalTo
import org.junit.Test
import org.hamcrest.MatcherAssert.assertThat

class RomanNumeralTest {

    @Test
    fun `Should test that 2 Roman numeral strings are summed together`() {
        val romanNumeral1 = RomanNumeral("X")
        val romanNumeral2 = RomanNumeral("X")
        val sumOfRomanNumerals = romanNumeral1 + romanNumeral2

        assertThat(sumOfRomanNumerals.toString(), equalTo("XX"))
    }

    @Test
    fun `Should test that 2 Roman numeral strings are summed together - bit more complicated`() {
        val romanNumeral1 = RomanNumeral("DCCCXLV")
        val romanNumeral2 = RomanNumeral("DCCCXLV")
        val sumOfRomanNumerals  = romanNumeral1 + romanNumeral2

        assertThat(sumOfRomanNumerals.romanNumeral, equalTo("MDCXC"))
    }

    @Test
    fun `Should test that 2 Roman numeral strings are summed together - bit more complicated 2 `() {
        val romanNumeral1 = RomanNumeral("MMXXII")
        val romanNumeral2 = RomanNumeral("MMXXII")
        val sumOfRomanNumerals  = romanNumeral1 + romanNumeral2

        assertThat(sumOfRomanNumerals.romanNumeral, equalTo("MMMMXLIV"))
    }

    @Test
    fun `Should test that a roman numeral subtractives are substituted - one subtractive substitution`() {
        val romanNumeral1 = RomanNumeral("IX")
        val removedSubtractives = romanNumeral1.removeSubtractives()

        assertThat(removedSubtractives.romanNumeral, equalTo("VIIII"))
    }

    @Test
    fun `Should test that a roman numeral subtractives are substituted - 3 subtractive substitutions `() {
        val romanNumeral1 = RomanNumeral("MCMXCIV")
        val removedSubtractives = romanNumeral1.removeSubtractives()

        assertThat(removedSubtractives.romanNumeral, equalTo("MDCCCCLXXXXIIII"))
    }

    @Test
    fun `Should test sorting a string of roman numerals into the correct order of precedence`() {
        val romanNumeral1 = RomanNumeral("MDCCCCLXXXXIIIIMDCCCCLXXXXIIII")
        val sortedRomanNumeral = romanNumeral1.sortRomanNumeral()

        assertThat(sortedRomanNumeral.romanNumeral, equalTo("MMDDCCCCCCCCLLXXXXXXXXIIIIIIII"))
    }

    @Test

    fun `Should test that a roman numeral is combined to highlest level numerals possible`() {
        val romanNumeral1 = RomanNumeral("DCCCCCCLXXXXXVVIIII")
        val compressedRomanNumeral = romanNumeral1.compressRomanNumeral()

        assertThat(compressedRomanNumeral.romanNumeral, equalTo("MCCXIIII"))
    }

    @Test
    fun `Should test the addition of subtractive elements of a roman numeral`() {
        val romanNumeral1 = RomanNumeral("MCCXIIII")
        val subtractiveAddedRomanNumeral = romanNumeral1.addSubtractives()

        assertThat(subtractiveAddedRomanNumeral.romanNumeral, equalTo("MCCXIV"))
    }
}