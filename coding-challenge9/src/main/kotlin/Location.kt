package codingchallenge9

import java.lang.IllegalArgumentException

const val INDUSTRY_PURCHASE_PRICE = 100
const val INDUSTRY_BASE_RENT = 20
const val GO_FEE = 100

enum class ShopType {
    MINISTORE,
    SUPERMARKET,
    MEGASTORE;
}

enum class DevelopmentStatus {
    UNDEVELOPED,
    MINISTORE,
    SUPERMARKET,
    MEGASTORE;
}

data class StoreBuildingCosts(
    val costOfBuildingMinistore: Money,
    val costOfBuildingSupermarket: Money,
    val costOfBuildingMegastore: Money
)

data class LocationRentalValues(
    val undevelopedRent: Money,
    val rentMinistore: Money,
    val rentSupermarket: Money,
    val rentMegastore: Money
)

data class LocationList(
    val locations: List<ILocation>
)

interface ILocation {
    val name: String
}

interface IRentable {
    fun getRent(): Money
}

interface IPurchaseable : ILocation {
    val purchasePrice: Money
}

interface IFeePayable : ILocation {
    val fee: Money
}

interface IBuildable : ILocation {
    val storeBuildingCosts: StoreBuildingCosts
    fun getBuildCost(_shopType: ShopType): Money
}

class FreeParking : ILocation {
    override val name: String = "Free Parking"
}

class Go : ILocation, IFeePayable {
    override val fee: Money = Money(GO_FEE)
    override val name: String = "Go"
}

class Industry(override val name: String) : IRentable, IPurchaseable, ILocation {

    override val purchasePrice: Money = Money(INDUSTRY_PURCHASE_PRICE)
    override fun getRent() = Money(INDUSTRY_BASE_RENT)
}

class RetailSite(
    override val name: String,
    override val purchasePrice: Money,
    costOfBuildingStores: StoreBuildingCosts,
    private val locationRentalValues: LocationRentalValues,
    groupID: Int
) : IRentable, IPurchaseable, IBuildable, ILocation {

    override val storeBuildingCosts: StoreBuildingCosts = costOfBuildingStores
    val retailGroup: Int = groupID

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
        if (retailDevelopmentStatus == DevelopmentStatus.UNDEVELOPED)
            retailDevelopmentStatus = DevelopmentStatus.MINISTORE
        else
            throw IllegalArgumentException("Cannot Build Ministore, something is already built that needs to be sold first!")

    }

    fun buildSupermarket() {
        if (retailDevelopmentStatus == DevelopmentStatus.MINISTORE)
            retailDevelopmentStatus = DevelopmentStatus.SUPERMARKET
        else
            throw IllegalArgumentException("Cannot Build Supermarket")
    }

    fun buildMegastore() {
        if (retailDevelopmentStatus == DevelopmentStatus.SUPERMARKET)
            retailDevelopmentStatus = DevelopmentStatus.MEGASTORE
        else
            throw IllegalArgumentException("Cannot Build Megastore")
    }
}
