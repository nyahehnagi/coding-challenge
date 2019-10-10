package codingchallenge14

import org.hamcrest.CoreMatchers.equalTo
import org.junit.Test
import org.hamcrest.MatcherAssert.assertThat


class FoldTest {

    @Test
    fun `Should test that an integer list is folded to a String using an empty string as the starting value`() {
        val integerList = listOf(1, 2, 3, 4)
        val foldedString = myFold(integerList, "") { acc, element -> acc + element.toString() }

        assertThat(foldedString, equalTo("1234"))
    }

    @Test
    fun `Should test that an integer list is folded to a String using an non empty string as the starting value`() {
        val integerList = listOf(1, 2, 3, 4)
        val foldedString = myFold(integerList, "The result is: ") { acc, element -> acc + element.toString() }

        assertThat(foldedString, equalTo("The result is: 1234"))
    }

    @Test
    fun `Should test that an integer list is folded to an int using myGenericFold`() {
        val integerList = listOf(1, 2, 3, 4)
        val folded = myGenericFold(integerList, 0) { acc, element -> acc + element }

        assertThat(folded, equalTo(10))
    }

    @Test
    fun `Should test that an integer list is folded to a String using myGenericFold`() {
        val integerList = listOf(1, 2, 3, 4)
        val folded = myGenericFold(integerList, "The result is: ") { acc, element -> acc + element.toString() }

        assertThat(folded, equalTo("The result is: 1234"))
    }

    @Test
    fun `Should test that a double list is folded to an Integer`() {
        val integerList = listOf(1.0, 2.0, 3.0, 4.3)
        val folded = myGenericFold(integerList, 0) { acc, element -> acc + element.toInt() }

        assertThat(folded, equalTo(10))
    }
}