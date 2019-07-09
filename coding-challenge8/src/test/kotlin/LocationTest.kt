package codingchallenge8

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class LocationTest {

    @Test
    fun `Should test that a Free Parking is correctly created`() {
        val freeParkinglocation = FreeParking()
        assertThat(freeParkinglocation.name, equalTo("Free Parking"))
    }

    @Test
    fun `Should test that the Go location is correctly created`(){
        val goLocation = Go()
        assertThat(goLocation.name, equalTo("Go"))
        assertThat(goLocation.fee.value, equalTo(100))
    }

    @Test
    fun `Should test that a warehouse location is correctly created`(){
        val warehouseLocation = Industry("Magna Park")
        assertThat(warehouseLocation.getRent().value, equalTo(20))
        assertThat(warehouseLocation.name, equalTo("Magna Park"))
        assertThat(warehouseLocation.isPurchaseable, equalTo(true))
    }


    @Test
    fun `Should test that a retail site location that is undeveloped `(){
        val retailSiteLocation = RetailSite("Oxford Street", GBP(100),StoreBuildingCosts(GBP(10),GBP(20),GBP(30)), LocationRentalValues(GBP(40),GBP(50),GBP(60),GBP(70)), 1)
        //val retailSiteLocation = RetailSite("Oxford Street", 200)
        assertThat(retailSiteLocation.getRent().value, equalTo(40))
        assertThat(retailSiteLocation.name, equalTo("Oxford Street"))
        assertThat(retailSiteLocation.getRent().value, equalTo(40))
        assertThat(retailSiteLocation.isPurchaseable, equalTo(true))
    }

    @Test
    fun `Should test that a retail site location that develops a megastore `() {
        val retailSiteLocation = RetailSite("Peter Jones", GBP(200), StoreBuildingCosts(GBP(10),GBP(20),GBP(30)), LocationRentalValues(GBP(40),GBP(50),GBP(60),GBP(70)), 1)
        retailSiteLocation.buildMegastore()
        assertThat(retailSiteLocation.getRent().value, equalTo(70))
        assertThat(retailSiteLocation.name, equalTo("Peter Jones"))
    }

    @Test
    fun `Should test that a retail site location that develops a supermarket `(){
        val retailSiteLocation = RetailSite("Peter Jones", GBP(200), StoreBuildingCosts(GBP(10),GBP(20),GBP(30)), LocationRentalValues(GBP(40),GBP(50),GBP(60),GBP(70)), 1)
        retailSiteLocation.buildSupermarket()
        assertThat(retailSiteLocation.getRent().value, equalTo(60))
        assertThat(retailSiteLocation.name, equalTo("Peter Jones"))
     }

    @Test
    fun `Should test that a retail site location that develops a ministore `(){
        val retailSiteLocation = RetailSite("Peter Jones", GBP(200),StoreBuildingCosts(GBP(10),GBP(20),GBP(30)),LocationRentalValues(GBP(40),GBP(50),GBP(60),GBP(70)), 1)
        retailSiteLocation.buildMiniStore()
        assertThat(retailSiteLocation.getRent().value, equalTo(50))
        assertThat(retailSiteLocation.name, equalTo("Peter Jones"))
     }
}

class GBPTest{
    @Test
    fun `should be a positive value for GBP when passed negative amount when created`(){
        val newGBP = GBP(-10)
        assertThat(newGBP.value, equalTo(10))
    }

    @Test
    fun `should return a string with pound sign prefixed `(){
        val newGBP = GBP(100)
        assertThat(newGBP.toString(), equalTo("£100"))

    }
}