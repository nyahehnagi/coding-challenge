import cc11.*
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Test
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import java.io.File

class PubandBeerTest {
    @Test
    fun `Should test that a list of beers is returned for a list of pubs and in alphabetical order`() {
        val pub1 = Pub(
            name = "The Boozer",
            postCode = "E11 3BE",
            regularBeers = listOf("Good Beer", "Bad Beer"),
            guestBeers = listOf("Average Beer"),
            pubService = "www.google.com",
            id = "1",
            branch = "AAA",
            createTS = "2019-05-13 19:31:39"
        )
        val pub2 = Pub(
            name = "The Little Boozer",
            postCode = "SE27 0SH",
            regularBeers = listOf("Meh Beer", "Awesome Beer"),
            guestBeers = listOf("Crap Beer"),
            pubService = "www.google.com",
            id = "2",
            branch = "BBB",
            createTS = "2019-05-13 19:31:39"
        )
        val pubList :List<Pub> = listOf(pub1,pub2)
        val beerList : List <Beer> = obtainListOfBeers(pubList)

        assertThat(beerList[0].beerName, equalTo("Average Beer"))
        assertThat(beerList[5].beerName, equalTo("Meh Beer"))
        assertThat(beerList.size.toString(), equalTo("6"))
    }

    @Test
    fun `Should test that the file that is passed in returns a pub list with no duplicates and the latest pub record is captured`(){
        val jsonFile = File("src/main/resources/pubjson.json")
        val pubList: List<Pub> = deserialisePubsJSON(jsonFile)

        assertThat(pubList.size.toString(), equalTo("3"))
        assertThat(pubList[0].createTS, equalTo("2019-05-15 19:31:39"))

    }

}

