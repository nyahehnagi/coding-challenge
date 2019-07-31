package codingchallenge9

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
                        location : Rentable ) : ITransaction {

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
                           location : Purchaseable ) : ITransaction {

        transactionHistory.add(PurchaseLocationTxn(fromAccountHolder,toAccountHolder,location))

        return transactionHistory.last()
    }

}
