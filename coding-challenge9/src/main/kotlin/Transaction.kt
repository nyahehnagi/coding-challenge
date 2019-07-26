
package codingchallenge9

import java.lang.IllegalArgumentException

const val STARTING_BALANCE = 200

enum class TransactionType{
        BANKTRANSFER,
        //BANKPAYMENT
        RENTPAYMENT ,
        //LOCATIONPURCHASE,
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

/*
object TransactionFactory(){
    companion object {
        fun createTransaction(
                    transactionType: TransactionType,
                    fromAccountHolder: IAccountHolder,
                    toAccountHolder: IAccountHolder
            /*,
                           //transactionData : TransactionData,
                           location : Location,


                           shopType: ShopType*/
        ): ITransaction {
            return when (transactionType) {
                TransactionType.BANKTRANSFER -> BankTransferTxn(fromAccountHolder, toAccountHolder)
                //TransactionType.BUILDSHOP-> BuildShopTxn (fromAccountHolder,toAccountHolder,location,shopType)
            }
        }
    }

}
*/

class BankTransferTxn ( _fromAccountHolder: IAccountHolder, _toAccountHolder : IAccountHolder): ITransaction{
    override val transactionAmount : Money = GBP (STARTING_BALANCE)
    override val fromAccountHolder : IAccountHolder = _fromAccountHolder
    override val toAccountHolder: IAccountHolder =  _toAccountHolder

}


class RentPaymentTxn (_fromAccountHolder: IAccountHolder, _toAccountHolder : IAccountHolder, _location:Location ): ITransaction{


    override val fromAccountHolder : IAccountHolder = _fromAccountHolder
    override val toAccountHolder: IAccountHolder =  _toAccountHolder
    override val transactionAmount : Money

    init{
        when (_location){
            is Industry -> transactionAmount = _location.getRent()
            is RetailSite -> transactionAmount = _location.getRent()
            else -> throw IllegalArgumentException ("Location cannot be rented")
        }

    }

}

class BuildShopTxn (_fromAccountHolder: IAccountHolder, _toAccountHolder : IAccountHolder, _shopType: ShopType, _location : RetailSite): ITransaction{


    override val fromAccountHolder : IAccountHolder = _fromAccountHolder
    override val toAccountHolder: IAccountHolder =  _toAccountHolder
    override val transactionAmount : Money =  _location.getBuildCost(_shopType)
    val shopLocation : RetailSite = _location
    val shopType : ShopType = _shopType


}

//class BankPaymentTxn (_fromAccountHolder : IAccountHolder,
 //                     _toAccountHolder: IAccountHolder ): ITransaction)
/*
BankPayment
from bank to player
Amount

RentPayment
from player to player
Amount

LocationPurchase
from player to bank
Amount

BuildShop
from player to bank
amount
location
building type
*/

/*
A function that adds a transaction for an amount being transferred from the Bank to a Player. This is the starting balance for each player in the game.

A function that adds a transaction for the Bank paying a fee to a Player, e.g. when the player passes through the Go location.

A function that adds a transaction for rent being paid from one player to another, e.g. when a player lands on a RetailSite location owned by another player.

A function that adds a transaction for when a Player has paid the Bank to purchase a Location.

A function that adds a transaction for when a Player has paid the Bank for building a specific type of building on a Location. Types of building include ministore, supermarket or megastore.
 */

