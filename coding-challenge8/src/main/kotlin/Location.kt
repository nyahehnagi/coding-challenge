package codingchallenge8


enum class LocationType(val locationString:String){
    GO("Go"),
    FREEPARKING("Free Parking"),
    WAREHOUSE ("Warehouse"),
    RETAILSITE ("Retail Site")
}

enum class ShopType (val shopValue:Int) {
    UNDEVELOPED (0),
    MINISTORE (1),
    SUPERMARKET (2),
    MEGASTORE (3);
}

data class StoreBuildingCosts ( val costOfBuildingMinistore: Int,
                                   val costOfBuildingSupermarket: Int,
                                   val costOfBuildingMegastore: Int)

data class LocationRentalValues (val undevelopedRent: Int,
                                 val rentMinistore: Int,
                                 val rentSupermarket: Int,
                                 val rentMegastore: Int)


abstract class Location (_name: String){

    val name: String = _name

    abstract val locationType: LocationType
    abstract val baseRent: Int
    abstract val passThroughValue: Int
    abstract val canBePurchased: Boolean
    abstract val purchasePrice: Int

    abstract fun GetRent():Int
}

class FreeParking() : Location("Free Parking"){

    override val passThroughValue:Int = 0
    override val canBePurchased: Boolean = false
    override val purchasePrice: Int = 0
    override val baseRent: Int = 0
    override val locationType:LocationType = LocationType.FREEPARKING

    override fun GetRent() = baseRent
}

class Go ():Location ("Go"){
    override val passThroughValue:Int = PASS_THROUGH_GO
    override val canBePurchased: Boolean = false
    override val purchasePrice: Int = 0
    override val baseRent: Int = 0
    override val locationType:LocationType = LocationType.GO

    override fun GetRent() = baseRent
}

class Warehouse(name:String) : Location(name){

    override val passThroughValue:Int = 0
    override val canBePurchased: Boolean = true
    override val purchasePrice: Int = WAREHOUSE_PURCHASE_PRICE
    override val baseRent: Int = WAREHOUSE_BASE_RENT
    override val locationType:LocationType = LocationType.WAREHOUSE

    override fun GetRent() = baseRent
}


class RetailSite(   name:String,
                    _purchasePrice:Int,
                    _costOfBuildingStores:StoreBuildingCosts,
                    _locationRentalValues:LocationRentalValues,
                    _groupID:Int
                    ) : Location(name){

    override val passThroughValue:Int = 0
    override val canBePurchased: Boolean = true
    override val purchasePrice: Int = _purchasePrice
    override val locationType:LocationType = LocationType.RETAILSITE
    override val baseRent: Int = _locationRentalValues.undevelopedRent

    val storeBuildingCosts: StoreBuildingCosts = _costOfBuildingStores
    val locationRentalValues: LocationRentalValues = _locationRentalValues
    val retailGroup: Int = _groupID

    private var retailDevelopmentStatus: ShopType = ShopType.UNDEVELOPED

    override fun GetRent(): Int {
        when (retailDevelopmentStatus){
            ShopType.UNDEVELOPED -> return baseRent
            ShopType.MINISTORE -> return locationRentalValues.rentMinistore
            ShopType.SUPERMARKET -> return locationRentalValues.rentSupermarket
            ShopType.MEGASTORE -> return locationRentalValues.rentMegastore
        }
    }

    fun BuildMiniStore (){
        retailDevelopmentStatus = ShopType.MINISTORE
    }

    fun BuildSupermarket (){
        retailDevelopmentStatus = ShopType.SUPERMARKET
    }

    fun BuildMegastore (){
        retailDevelopmentStatus = ShopType.MEGASTORE
    }
}