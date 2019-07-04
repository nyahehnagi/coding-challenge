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
    override val passThroughValue:Int = 100
    override val canBePurchased: Boolean = false
    override val purchasePrice: Int = 0
    override val baseRent: Int = 0
    override val locationType:LocationType = LocationType.GO

    override fun GetRent() = baseRent
}

class Warehouse(name:String) : Location(name){

    override val passThroughValue:Int = 0
    override val canBePurchased: Boolean = true
    override val purchasePrice: Int = 100
    override val baseRent: Int = 20
    override val locationType:LocationType = LocationType.WAREHOUSE

    override fun GetRent() = baseRent

}


class RetailSite(   name:String,
                    _purchasePrice:Int,
                    _costOfBuildingStores:String,
                    _rent:String,
                    _groupID:Int
                    ) : Location(name){

    override val passThroughValue:Int = 0
    override val canBePurchased: Boolean = true
    override val purchasePrice: Int = _purchasePrice

    override val locationType:LocationType = LocationType.RETAILSITE

    val costOfBuildingMinistore: Int = _costOfBuildingStores.split(",")[0].toInt()
    val costOfBuildingSupermarket: Int = _costOfBuildingStores.split(",")[1].toInt()
    val costOfBuildingMegastore: Int = _costOfBuildingStores.split(",")[2].toInt()

    override val baseRent: Int = _rent.split(",")[0].toInt()

    val rentMinistore: Int = _rent.split(",")[1].toInt()
    val rentSupermarket: Int = _rent.split(",")[2].toInt()
    val rentMegastore: Int = _rent.split(",")[3].toInt()

    val groupID: Int = _groupID
    var developmentStatus: ShopType = ShopType.UNDEVELOPED

    override fun GetRent(): Int {
        when (developmentStatus){
            ShopType.UNDEVELOPED -> return baseRent
            ShopType.MINISTORE -> return rentMinistore
            ShopType.SUPERMARKET -> return rentSupermarket
            ShopType.MEGASTORE -> return rentMegastore
        }
    }
}