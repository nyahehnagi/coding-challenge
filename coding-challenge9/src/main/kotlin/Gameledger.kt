package codingchallenge9

import java.lang.IllegalArgumentException

const val ERROR_INVALID_TRANSACTION_TYPE = "Invalid Transaction Type for passed parameters"


class Gameledger {
    private val transactionHistory: MutableList<ITransaction> = mutableListOf()

    // Bank Transfer
    fun addBankTransferTransaction (    fromAccountHolder : IAccountHolder,
                                        toAccountHolder : IAccountHolder) : ITransaction {

        transactionHistory.add(BankTransferTxn(fromAccountHolder, toAccountHolder))

        return transactionHistory.last()
    }

    // Build Shop
    fun addBuildShopTransaction ( fromAccountHolder : IAccountHolder,
                        toAccountHolder : IAccountHolder,
                        location : RetailSite ,
                        shopType: ShopType ) : ITransaction {

        transactionHistory.add(BuildShopTxn(fromAccountHolder,toAccountHolder,shopType,location))

        return transactionHistory.last()
    }

    fun addRentPaymentTransaction ( fromAccountHolder : IAccountHolder,
                        toAccountHolder : IAccountHolder,
                        location : Location ) : ITransaction {

        transactionHistory.add(RentPaymentTxn(fromAccountHolder,toAccountHolder,location))

        return transactionHistory.last()
    }

    fun addFeeTransaction (fromAccountHolder : IAccountHolder,
                                   toAccountHolder : IAccountHolder,
                                   location : Location ) : ITransaction {

        transactionHistory.add(BankFeeTxn(fromAccountHolder,toAccountHolder,location))

        return transactionHistory.last()
    }

    fun addPurchaseTransaction (fromAccountHolder : IAccountHolder,
                           toAccountHolder : IAccountHolder,
                           location : Location ) : ITransaction {

        transactionHistory.add(PurchaseLocationTxn(fromAccountHolder,toAccountHolder,location))

        return transactionHistory.last()
    }

    /*
    // Pay Rent, Fee or Purchase a location
    fun addTransaction (transactionType : TransactionType,
                        fromAccountHolder : IAccountHolder,
                        toAccountHolder : IAccountHolder,
                        location : Location ) : ITransaction {

        when (transactionType){
            TransactionType.RENTPAYMENT -> transactionHistory.add(RentPaymentTxn(fromAccountHolder,toAccountHolder,location))
            TransactionType.BANKPAYMENT -> transactionHistory.add(BankFeeTxn(fromAccountHolder,toAccountHolder,location))
            TransactionType.LOCATIONPURCHASE -> transactionHistory.add(PurchaseLocationTxn(fromAccountHolder,toAccountHolder,location))
            else -> throw IllegalArgumentException (ERROR_INVALID_TRANSACTION_TYPE)
        }
        return transactionHistory.last()
    }

     */
}
