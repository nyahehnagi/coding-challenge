package codingchallenge8

enum class ShopType {
    UNDEVELOPED,
    MINISTORE ,
    SUPERMARKET,
    MEGASTORE;
}

data class StoreBuildingCosts ( val costOfBuildingMinistore: Money,
                                   val costOfBuildingSupermarket: Money,
                                   val costOfBuildingMegastore: Money)

data class LocationRentalValues (val undevelopedRent: Money,
                                 val rentMinistore: Money,
                                 val rentSupermarket: Money,
                                 val rentMegastore: Money)

sealed class Location (_name: String){

    val name: String = _name

    abstract val baseRent: Money
    abstract fun getRent(): Money

    // pondering if I add whether a location is purchasable
    // do I add whether
}

class FreeParking : Location("Free Parking"){

    override val baseRent: Money = GBP (0)
    override fun getRent() = baseRent
}

class Go (_fee: Money): Location ("Go"){

    val fee : Money = _fee
    override val baseRent: Money = GBP (0)
    override fun getRent() = baseRent
}

class Warehouse(name:String) : Location(name){

    override val baseRent: Money = GBP (WAREHOUSE_BASE_RENT)
    override fun getRent() = baseRent

    val purchasePrice: Money = GBP (WAREHOUSE_PURCHASE_PRICE)

}


class RetailSite : Location {

    constructor(name: String, _purchasePrice: Money, _costOfBuildingStores: StoreBuildingCosts, _locationRentalValues: LocationRentalValues, _groupID: Int) : super(name) {
        this.purchasePrice = _purchasePrice
        this.baseRent = _locationRentalValues.undevelopedRent
        this.locationRentalValues = _locationRentalValues
        this.storeBuildingCosts = _costOfBuildingStores
        this.retailGroup = _groupID
        this.retailDevelopmentStatus = ShopType.UNDEVELOPED
    }

    override val baseRent: Money

    private val locationRentalValues: LocationRentalValues
    private var retailDevelopmentStatus: ShopType

    val storeBuildingCosts: StoreBuildingCosts
    val retailGroup: Int
    val purchasePrice: Money



    override fun getRent(): Money {
        when (retailDevelopmentStatus){
            ShopType.UNDEVELOPED -> return baseRent
            ShopType.MINISTORE -> return locationRentalValues.rentMinistore
            ShopType.SUPERMARKET -> return locationRentalValues.rentSupermarket
            ShopType.MEGASTORE -> return locationRentalValues.rentMegastore
        }
    }

    fun buildMiniStore (){
        retailDevelopmentStatus = ShopType.MINISTORE
    }

    fun buildSupermarket (){
        retailDevelopmentStatus = ShopType.SUPERMARKET
    }

    fun buildMegastore (){
        retailDevelopmentStatus = ShopType.MEGASTORE
    }
}

sealed class Money(_value: Int){
    abstract val value: Int
}

class GBP (_value:Int): Money(_value){
    override val value:Int
    init{
        // rules state that GBP cannot be negative
        if (_value >= 0) {value = _value} else value = - _value
    }

    override fun toString(): String {
        return "£$value"
    }

}