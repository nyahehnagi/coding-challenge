package codingchallenge9

const val INDUSTRY_PURCHASE_PRICE = 100
const val INDUSTRY_BASE_RENT = 20
const val GO_FEE = 100

enum class ShopType {
    MINISTORE ,
    SUPERMARKET,
    MEGASTORE;
}

enum class DevelopmentStatus {
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

interface ILocation {
    val name: String
}

interface IRentable {
    fun getRent () : Money
}

interface IPurchaseable : ILocation{
    val  purchasePrice: Money
}

interface IFeePayable : ILocation {
    val fee : Money
}

interface IBuildable : ILocation {
    val storeBuildingCosts: StoreBuildingCosts
    fun getBuildCost (_shopType: ShopType): Money
}

class FreeParking : ILocation {
    override val name: String = "Free Parking"
}

class Go : ILocation, IFeePayable {
    override val fee : Money = GBP(GO_FEE)
    override val name : String = "Go"
}

class Industry(_name:String) : IRentable, IPurchaseable, ILocation{

    override val name: String = _name
    override val purchasePrice: Money = GBP (INDUSTRY_PURCHASE_PRICE)
    override fun getRent() = GBP(INDUSTRY_BASE_RENT)
}

class RetailSite (  _name: String,
                    _purchasePrice: Money,
                    _costOfBuildingStores: StoreBuildingCosts,
                    _locationRentalValues: LocationRentalValues,
                    _groupID: Int) : IRentable, IPurchaseable, IBuildable, ILocation {

    override val purchasePrice: Money = _purchasePrice
    override val name: String = _name
    override val storeBuildingCosts: StoreBuildingCosts = _costOfBuildingStores
    val retailGroup: Int = _groupID

    private val locationRentalValues: LocationRentalValues = _locationRentalValues
    private var retailDevelopmentStatus: DevelopmentStatus = DevelopmentStatus.UNDEVELOPED


    override fun getRent(): Money = when (retailDevelopmentStatus) {
        DevelopmentStatus.UNDEVELOPED -> locationRentalValues.undevelopedRent
        DevelopmentStatus.MINISTORE -> locationRentalValues.rentMinistore
        DevelopmentStatus.SUPERMARKET -> locationRentalValues.rentSupermarket
        DevelopmentStatus.MEGASTORE -> locationRentalValues.rentMegastore
    }

    override fun getBuildCost(_shopType: ShopType): Money = when (_shopType) {
        ShopType.MINISTORE -> storeBuildingCosts.costOfBuildingMinistore
        ShopType.SUPERMARKET -> storeBuildingCosts.costOfBuildingSupermarket
        ShopType.MEGASTORE -> storeBuildingCosts.costOfBuildingMegastore
    }

    // Thinking of getting rid of these and taking the building of store outside of the location class
    fun buildMiniStore() {
        retailDevelopmentStatus = DevelopmentStatus.MINISTORE
    }
    fun buildSupermarket() {
            retailDevelopmentStatus = DevelopmentStatus.SUPERMARKET
        }

    fun buildMegastore() {
            retailDevelopmentStatus = DevelopmentStatus.MEGASTORE
        }
}
