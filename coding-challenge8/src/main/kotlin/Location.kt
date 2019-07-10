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
}

interface Rent {
    val baseRent : Money
}

interface Purchaseable{
    val isPurchaseable : Boolean
}

class FreeParking : Location("Free Parking")

class Go : Location ("Go"){

    val fee : Money = GBP(GO_FEE)
}

class Industry(name:String) : Rent, Purchaseable, Location(name){

    override val baseRent : Money = GBP(INDUSTRY_BASE_RENT)
    override val isPurchaseable:Boolean = true
    fun getRent() = baseRent

    val purchasePrice: Money = GBP (INDUSTRY_PURCHASE_PRICE)

}


class RetailSite (  name: String,
                    _purchasePrice: Money,
                    _costOfBuildingStores: StoreBuildingCosts,
                    _locationRentalValues: LocationRentalValues,
                    _groupID: Int) : Rent, Purchaseable, Location (name) {

    override val baseRent : Money
    override val isPurchaseable:Boolean = true

    private val locationRentalValues: LocationRentalValues
    private var retailDevelopmentStatus: ShopType = ShopType.UNDEVELOPED

    val storeBuildingCosts: StoreBuildingCosts
    val retailGroup: Int
    val purchasePrice: Money


    init {
        baseRent  = _locationRentalValues.undevelopedRent
        locationRentalValues = _locationRentalValues
        storeBuildingCosts = _costOfBuildingStores
        retailGroup= _groupID
        purchasePrice = _purchasePrice
    }


    fun getRent(): Money = when (retailDevelopmentStatus) {
            ShopType.UNDEVELOPED -> baseRent
            ShopType.MINISTORE -> locationRentalValues.rentMinistore
            ShopType.SUPERMARKET -> locationRentalValues.rentSupermarket
            ShopType.MEGASTORE ->  locationRentalValues.rentMegastore
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
        value = if (_value >= 0) {_value} else - _value
    }

    override fun toString(): String {
        return "£$value"
    }

}