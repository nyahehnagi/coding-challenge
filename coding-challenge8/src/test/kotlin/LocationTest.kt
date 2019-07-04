package codingchallenge8

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class LocationTest {

    @Test
    fun `Should test that a Free Parking is correctly created`() {
        val freeParkinglocation = FreeParking()
        assertThat(freeParkinglocation.locationType.locationString, equalTo("Free Parking"))
        assertThat(freeParkinglocation.GetRent(), equalTo(0))
        assertThat(freeParkinglocation.name, equalTo("Free Parking"))
        assertThat(freeParkinglocation.canBePurchased, equalTo(false))
    }

    @Test
    fun `Should test that the Go location is correctly created`(){
        val goLocation = Go()
        assertThat(goLocation.locationType.locationString, equalTo("Go"))
        assertThat(goLocation.GetRent(), equalTo(0))
        assertThat(goLocation.name, equalTo("Go"))
        assertThat(goLocation.passThroughValue, equalTo(100))
        assertThat(goLocation.canBePurchased, equalTo(false))
    }

    @Test
    fun `Should test that a warehouse location is correctly created`(){
        val warehouseLocation = Warehouse("Magna Park")
        assertThat(warehouseLocation.locationType.locationString, equalTo("Warehouse"))
        assertThat(warehouseLocation.GetRent(), equalTo(20))
        assertThat(warehouseLocation.name, equalTo("Magna Park"))
        assertThat(warehouseLocation.passThroughValue, equalTo(0))
        assertThat(warehouseLocation.canBePurchased, equalTo(true))
    }


    @Test
    fun `Should test that a retail site location that is undeveloped `(){
        val retailSiteLocation = RetailSite("Oxford Street", 200,"10,20,30","40,50,60,70", 1)
        //val retailSiteLocation = RetailSite("Oxford Street", 200)
        assertThat(retailSiteLocation.locationType.locationString, equalTo("Retail Site"))
        assertThat(retailSiteLocation.GetRent(), equalTo(40))
        assertThat(retailSiteLocation.name, equalTo("Oxford Street"))
        assertThat(retailSiteLocation.canBePurchased, equalTo(true))
        assertThat(retailSiteLocation.costOfBuildingMegastore, equalTo(30))
    }

    @Test
    fun `Should test that a retail site location that develops a megastore `(){
        val retailSiteLocation = RetailSite("Peter Jones", 200,"10,20,30","40,50,60,70", 1)
        retailSiteLocation.developmentStatus = ShopType.MEGASTORE
        //val retailSiteLocation = RetailSite("Oxford Street", 200)
        assertThat(retailSiteLocation.locationType.locationString, equalTo("Retail Site"))
        assertThat(retailSiteLocation.GetRent(), equalTo(70))
        assertThat(retailSiteLocation.name, equalTo("Peter Jones"))
        assertThat(retailSiteLocation.canBePurchased, equalTo(true))
        assertThat(retailSiteLocation.costOfBuildingMegastore, equalTo(30))
    }
}
