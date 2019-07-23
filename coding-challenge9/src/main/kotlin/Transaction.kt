
package codingchallenge9

enum class TransactionType{
        BANKTRANSFER,
        //BANKPAYMENT
        //RENTPAYMENT ,
        //LOCATIONPURCHASE,
        BUILDSHOP
}

interface IAccountHolder {
}

interface ITransaction{
    val transactionAmount : Money
    val fromAccountHolder : IAccountHolder
    val toAccountHolder: IAccountHolder
}

data class TransactionData (val transactionAmount : Money ,
                            val fromAccountHolder : IAccountHolder,
                            val toAccountHolder: IAccountHolder,
                            val location: Location? = null,
                            val shopType: ShopType = ShopType.UNDEVELOPED

)

class TransactionFactory(){
    fun createTransaction (transactionType : TransactionType,
                           transactionData : TransactionData):ITransaction{
        return when (transactionType){
            TransactionType.BANKTRANSFER -> BankTransfer(transactionData.transactionAmount, transactionData.fromAccountHolder , transactionData.toAccountHolder)
            TransactionType.BUILDSHOP-> BuildShop(transactionData.location, transactionData.shopType, transactionData.transactionAmount, transactionData.fromAccountHolder , transactionData.toAccountHolder)

        }
    }
}


class BankTransfer (_transactionAmount : Money, _fromAccountHolder : IAccountHolder, _toAccountHolder: IAccountHolder): ITransaction{
    override val transactionAmount : Money = _transactionAmount
    override val fromAccountHolder : IAccountHolder = _fromAccountHolder
    override val toAccountHolder: IAccountHolder = _toAccountHolder
}

class BuildShop (_location : RetailSite,
                 _shopType: ShopType,
                 _transactionAmount : Money,
                 _fromAccountHolder : IAccountHolder,
                 _toAccountHolder: IAccountHolder ): ITransaction{
    override val transactionAmount : Money = _location.getBuildCost(_shopType)
    override val fromAccountHolder : IAccountHolder = _fromAccountHolder
    override val toAccountHolder: IAccountHolder = _toAccountHolder
}

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

