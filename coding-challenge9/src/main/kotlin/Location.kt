package codingchallenge9

const val INDUSTRY_PURCHASE_PRICE = 100
const val INDUSTRY_BASE_RENT = 20
const val GO_FEE = 100

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

    fun getBuildCost(_shoptype: ShopType) : Money = when (_shoptype){
        ShopType.UNDEVELOPED -> GBP(0)
        ShopType.MINISTORE -> storeBuildingCosts.costOfBuildingMinistore
        ShopType.SUPERMARKET -> storeBuildingCosts.costOfBuildingSupermarket
        ShopType.MEGASTORE ->  storeBuildingCosts.costOfBuildingMegastore
    }

    // Thinking of getting rid of these and taking the building of store outside of the location class
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