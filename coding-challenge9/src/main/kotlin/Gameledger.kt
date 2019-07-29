package codingchallenge9

import java.lang.IllegalArgumentException

const val ERROR_INVALID_TRANSACTION_TYPE = "Invalid Transaction Type for passed parameters"


class Gameledger {
    private val transactionHistory: MutableList<ITransaction> = mutableListOf()


    // Bank Transfer
    fun addTransaction (transactionType : TransactionType,
                        fromAccountHolder : IAccountHolder,
                        toAccountHolder : IAccountHolder) : ITransaction {

        when (transactionType){
            TransactionType.BANKTRANSFER -> transactionHistory.add(BankTransferTxn(fromAccountHolder, toAccountHolder))
            else -> throw IllegalArgumentException (ERROR_INVALID_TRANSACTION_TYPE)
        }

        return transactionHistory.last()
    }

    // Build Shop
    fun addTransaction (transactionType : TransactionType,
                        fromAccountHolder : IAccountHolder,
                        toAccountHolder : IAccountHolder,
                        location : RetailSite ,
                        shopType: ShopType ) : ITransaction {

        if (transactionType == TransactionType.BUILDSHOP ){
            transactionHistory.add(BuildShopTxn(fromAccountHolder,toAccountHolder,shopType,location))
            return transactionHistory.last()
        }
        else
            throw IllegalArgumentException (ERROR_INVALID_TRANSACTION_TYPE)
    }

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
}
