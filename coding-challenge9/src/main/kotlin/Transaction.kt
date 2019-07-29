
package codingchallenge9

import java.lang.IllegalArgumentException

const val ERROR_LOCATION_CANNOT_BE_RENTED = "Location cannot be rented"
const val ERROR_LOCATION_HAS_NO_FEE = "Location has no fee"
const val ERROR_LOCATION_CANNOT_BE_PURCHASED = "Location cannot be purchased"
const val ERROR_INVALID_ACCOUNT = "Cannot purchase from this account type"

const val STARTING_BALANCE = 200

enum class TransactionType{
        BANKTRANSFER,
        BANKPAYMENT,
        RENTPAYMENT ,
        LOCATIONPURCHASE,
        BUILDSHOP
}

interface IAccountHolder {
    val name : String
}

interface ITransaction
{
    val transactionAmount : Money
    val fromAccountHolder : IAccountHolder
    val toAccountHolder: IAccountHolder
}

data class TransactionData (val toAccountHolder: IAccountHolder,
                            val fromAccountHolder : IAccountHolder,
                            val transactionAmount : Money,
                            val location: Location? = null,
                            val shopType: ShopType = ShopType.UNDEVELOPED

)

class Bank (): IAccountHolder {
    override val name = "bank"
}

class Player(_name : String): IAccountHolder{
    override val name = _name //should add some logic here to not allow any reserved names e.g bank
}

class BankTransferTxn ( _fromAccountHolder: IAccountHolder, _toAccountHolder : IAccountHolder): ITransaction{
    override val transactionAmount : Money = GBP (STARTING_BALANCE)
    override val fromAccountHolder : IAccountHolder = _fromAccountHolder
    override val toAccountHolder: IAccountHolder =  _toAccountHolder

}

class BankFeeTxn ( _fromAccountHolder: IAccountHolder, _toAccountHolder : IAccountHolder, _location:Location): ITransaction{

    override val fromAccountHolder : IAccountHolder = _fromAccountHolder
    override val toAccountHolder: IAccountHolder =  _toAccountHolder
    override val transactionAmount : Money

    init{
        when (_location){
            is Go -> transactionAmount = _location.fee
            else -> throw IllegalArgumentException (ERROR_LOCATION_HAS_NO_FEE)
        }

    }
}

class RentPaymentTxn (_fromAccountHolder: IAccountHolder, _toAccountHolder : IAccountHolder, _location:Location ): ITransaction{

    override val fromAccountHolder : IAccountHolder = _fromAccountHolder
    override val toAccountHolder: IAccountHolder =  _toAccountHolder
    override val transactionAmount : Money

    init{
        when (_location){
            is Industry -> transactionAmount = _location.getRent()
            is RetailSite -> transactionAmount = _location.getRent()
            else -> throw IllegalArgumentException (ERROR_LOCATION_CANNOT_BE_RENTED)
        }
    }
}

class PurchaseLocationTxn (_fromAccountHolder: IAccountHolder, _toAccountHolder : IAccountHolder, _location:Location ): ITransaction {
    override val toAccountHolder: IAccountHolder =  _toAccountHolder
    override val fromAccountHolder : IAccountHolder
    override val transactionAmount : Money
    val locationPurchased : Location

    init{
        //TODO Not to happy about this, I think the location should know whether it can be purchased, not the transaction. Revisit this
        when (_location){
            is Industry -> transactionAmount = _location.purchasePrice
            is RetailSite -> transactionAmount = _location.purchasePrice
            else -> throw IllegalArgumentException (ERROR_LOCATION_CANNOT_BE_PURCHASED)
        }

        when (_toAccountHolder){
            is Bank -> fromAccountHolder = _fromAccountHolder // Can purchase from the bank only
            else -> throw IllegalArgumentException (ERROR_INVALID_ACCOUNT) //Although in the future the rules may allow Player to Player purchase
        }
        locationPurchased = _location
    }
}

class BuildShopTxn (_fromAccountHolder: IAccountHolder, _toAccountHolder : IAccountHolder, _shopType: ShopType, _location : RetailSite): ITransaction{


    override val fromAccountHolder : IAccountHolder = _fromAccountHolder
    override val toAccountHolder: IAccountHolder =  _toAccountHolder
    override val transactionAmount : Money =  _location.getBuildCost(_shopType)
    val shopLocation : RetailSite = _location
    val shopType : ShopType = _shopType
}

