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

interface Location {
    val name: String
}

interface Rentable {
    fun getRent () : Money
}

interface Purchaseable : Location{
    val  purchasePrice: Money
}

class FreeParking(): Location {
    override val name: String = "Free Parking"
}

class Go (): Location {
    override val name: String = "Go"
    val fee : Money = GBP(GO_FEE)
}

class Industry(name:String) : Rentable, Purchaseable, Location{

    override val name: String = name
    override val purchasePrice: Money = GBP (INDUSTRY_PURCHASE_PRICE)
    override fun getRent() = GBP(INDUSTRY_BASE_RENT)
}

class RetailSite (  name: String,
                    _purchasePrice: Money,
                    _costOfBuildingStores: StoreBuildingCosts,
                    _locationRentalValues: LocationRentalValues,
                    _groupID: Int) : Rentable, Purchaseable, Location {

    override val purchasePrice: Money
    override val name: String = name
    private val locationRentalValues: LocationRentalValues
    private var retailDevelopmentStatus: ShopType = ShopType.UNDEVELOPED

    val storeBuildingCosts: StoreBuildingCosts
    val retailGroup: Int

    init {
        locationRentalValues = _locationRentalValues
        storeBuildingCosts = _costOfBuildingStores
        retailGroup= _groupID
        purchasePrice = _purchasePrice
    }

    override fun getRent(): Money = when (retailDevelopmentStatus) {
        ShopType.UNDEVELOPED -> locationRentalValues.undevelopedRent
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