package codingchallenge8

import codingchallenge8.ShopType.UNDEVELOPED

enum class ShopType {
    UNDEVELOPED,
    MINISTORE ,
    SUPERMARKET,
    MEGASTORE;
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

    abstract val baseRent: Int
    abstract fun getRent():Int
}

class FreeParking : Location("Free Parking"){

    override val baseRent: Int = 0
    override fun getRent() = baseRent
}

class Go : Location ("Go"){
    override val baseRent: Int = 0
    override fun getRent() = baseRent
}

class Warehouse(name:String) : Location(name){

    override val baseRent: Int = WAREHOUSE_BASE_RENT
    override fun getRent() = baseRent

    val purchasePrice: Int = WAREHOUSE_PURCHASE_PRICE

}


class RetailSite : Location {

    constructor(name: String, _purchasePrice: Int, _costOfBuildingStores: StoreBuildingCosts, _locationRentalValues: LocationRentalValues, _groupID: Int) : super(name) {
        this.purchasePrice = _purchasePrice
        this.baseRent = _locationRentalValues.undevelopedRent
        this.locationRentalValues = _locationRentalValues
        this.storeBuildingCosts = _costOfBuildingStores
        this.retailGroup = _groupID
        this.retailDevelopmentStatus = UNDEVELOPED
    }

    override val baseRent: Int

    private val locationRentalValues: LocationRentalValues

    val storeBuildingCosts: StoreBuildingCosts
    val retailGroup: Int
    val purchasePrice: Int

    private var retailDevelopmentStatus: ShopType

    override fun getRent(): Int {
        when (retailDevelopmentStatus){
            UNDEVELOPED -> return baseRent
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