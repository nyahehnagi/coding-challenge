package codingchallenge8


enum class LocationType(val locationString:String){
    NOTDEFINED (""),
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
    abstract val rent: Int
    abstract val passThroughValue: Int
    abstract val canBePurchased: Boolean
    abstract val purchasePrice: Int

}

class FreeParking() : Location("Free Parking"){

    override var passThroughValue:Int = 0
    override var canBePurchased: Boolean = false
    override var purchasePrice: Int = 0
    override var rent: Int = 0
    override var locationType:LocationType = LocationType.FREEPARKING

}

class Go ():Location ("Go"){
    override var passThroughValue:Int = 100
    override var canBePurchased: Boolean = false
    override var purchasePrice: Int = 0
    override var rent: Int = 0
    override var locationType:LocationType = LocationType.GO
}

class Warehouse(name:String) : Location(name){

    override var passThroughValue:Int = 0
    override var canBePurchased: Boolean = true
    override var purchasePrice: Int = 100
    override var rent: Int = 20
    override var locationType:LocationType = LocationType.WAREHOUSE

}


class RetailSite(   name:String,
                    _purchasePrice:Int
                    ) : Location(name){

    override var passThroughValue:Int = 0
    override var canBePurchased: Boolean = true
    override var purchasePrice: Int = _purchasePrice
    override var rent: Int = 20
    override var locationType:LocationType = LocationType.RETAILSITE

    val developmentStatus: ShopType = ShopType.UNDEVELOPED
    val costOfBuildingMinistore: Int = 0
    val costOfBuildingSupermarket: Int = 0
    val costOfBuildingMegastore: Int = 0

}
// ("Oxford Street", 200,"10,20,30","40,50,60,70", 1)