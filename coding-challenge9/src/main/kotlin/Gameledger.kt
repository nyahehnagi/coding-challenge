package codingchallenge9

import java.lang.IllegalArgumentException


class Gameledger {
    private val transactionHistory: MutableList<ITransaction> = mutableListOf()


    // Bank Transfer
    fun addTransaction (transactionType : TransactionType,
                        fromAccountHolder : IAccountHolder,
                        toAccountHolder : IAccountHolder) : ITransaction {

        when (transactionType){
            TransactionType.BANKTRANSFER -> transactionHistory.add(BankTransferTxn(fromAccountHolder, toAccountHolder))

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
            throw IllegalArgumentException ("Invalid Transaction Type for passed parameters")
    }

    // Pay Rent or Fee
    fun addTransaction (transactionType : TransactionType,
                        fromAccountHolder : IAccountHolder,
                        toAccountHolder : IAccountHolder,
                        location : Location ) : ITransaction {

        when (transactionType){
            TransactionType.RENTPAYMENT -> transactionHistory.add(RentPaymentTxn(fromAccountHolder,toAccountHolder,location))
            TransactionType.BANKPAYMENT -> transactionHistory.add(BankFeeTxn(fromAccountHolder,toAccountHolder,location))
            else -> throw IllegalArgumentException ("Invalid Transaction Type for passed parameters")
        }

        return transactionHistory.last()
    }
}