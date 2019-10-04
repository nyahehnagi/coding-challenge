import codingchallenge13.GeoLocation
import codingchallenge13.Store
import codingchallenge13.deserialiseStoreString

import org.hamcrest.CoreMatchers.equalTo
import org.junit.Test
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import kotlin.math.roundToInt

class ShopDistancesTest {

    @Test
    fun `Should test that a Geolocation is created`() {
        val longitude: Double = 1.23456
        val latitude: Double = 2.3456
        val geoLocation: GeoLocation = GeoLocation(latitude, longitude)

        assertThat(geoLocation.latitide, equalTo(latitude))
        assertThat(geoLocation.longitude, equalTo(longitude))
    }

    @Test
    fun `Should test that a Store is created`() {
        val storeName: String = "Victoria"
        val storePostCode: String = "SW1E 5NN"
        val storeGeoLocation: GeoLocation = GeoLocation(1.2345, 2.3456)

        val newStore: Store = Store(storeName, storePostCode, storeGeoLocation)

        assertThat(newStore.storeName, equalTo(storeName))
        assertThat(newStore.storePostCode, equalTo(storePostCode))
        assertThat(newStore.geoLocation.longitude, equalTo(storeGeoLocation.longitude))
    }

    @Test
    fun `Should test that a list of stores is created from a comma delimited string`() {
        val storeString:  String = "Victoria,SW1E 5NN,1.2345,2.3456,West Norwood,SE27 0SH,3.4567,4.5678"
        val storeList = deserialiseStoreString(storeString)
        assertThat(storeList[0].geoLocation.longitude, equalTo(1.2345))
        assertThat(storeList[0].storePostCode, equalTo("SW1E 5NN"))
        assertThat(storeList[0].storeName, equalTo("Victoria"))
        assertThat(storeList[1].geoLocation.longitude, equalTo(3.4567))
        assertThat(storeList[1].storeName, equalTo("West Norwood"))
        assertThat(storeList[1].storePostCode, equalTo("SE27 0SH"))
    }

    @Test
    fun `Should test that a list of stores is created from a comma delimited string and with missing data for geolocation`() {
        val storeString:  String = "Victoria,SW1E 5NN,1.2345,2.3456,Crap Store, CR1,,,West Norwood,SE27 0SH,3.4567,4.5678"
        val storeList = deserialiseStoreString(storeString)
        assertThat(storeList[0].storeGeoLocation.longitude, equalTo(2.3456))
        assertThat(storeList[0].storePostCode, equalTo("SW1E 5NN"))
        assertThat(storeList[0].storeName, equalTo("Victoria"))
        assertThat(storeList[1].storeGeoLocation.longitude, equalTo(4.5678))
        assertThat(storeList[1].storeName, equalTo("West Norwood"))
        assertThat(storeList[1].storePostCode, equalTo("SE27 0SH"))
    }

    @Test
    fun `Should test the haversine distance between 2 points`() {
        val geoLocation1 : GeoLocation = GeoLocation(51.498149,-0.142601) //171 Victoria
        val geoLocation2 : GeoLocation = GeoLocation(51.515057, -0.145109) //Oxford Street

        val haversineDistance = geoLocation1.haversine(geoLocation2).roundToInt()

        assertThat(haversineDistance, equalTo(1889)) //value checked with an online calculator

    }

    @Test
    fun `Should test the haversine distance between 2 points at the same place`() {
        val geoLocation1 : GeoLocation = GeoLocation(51.498149,-0.142601) //171 Victoria
        val geoLocation2 : GeoLocation = GeoLocation(51.498149,-0.142601) //171 Victoria

        val haversineDistance = geoLocation1.haversine(geoLocation2).roundToInt()
        assertThat(haversineDistance, equalTo(0)) //value checked with an online calculator
    }

    @Test
    fun `Should return the store with the shortest distance to current location`() {
        val geoLocation1 : GeoLocation = GeoLocation(51.498149,-0.142601) //171 Victoria
        val geoLocation2 : GeoLocation =  GeoLocation(51.515057, -0.145109) //Oxford Street
        val geoLocation3 : GeoLocation = GeoLocation(51.818294, -3.028245) //Abergavenny
        val geoLocation4: GeoLocation = GeoLocation(51.672083, -1.279705) //Abingdon

        val currentStore :Store = Store("Abergavenny","Post Code",geoLocation3)
        val storeList : List<Store> = listOf(Store("Oxford Street","Post Code",geoLocation2), Store("171 Victoria","Post Code",geoLocation1),Store("Abingdon","Post Code",geoLocation4))

        val closestStore = currentStore.findClosestShop(storeList)


        assertThat(closestStore.storeName, equalTo("Abingdon")) //value checked with an online calculator
    }

}