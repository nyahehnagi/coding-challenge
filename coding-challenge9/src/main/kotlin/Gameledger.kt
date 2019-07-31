package codingchallenge9


object Gameledger {

    private val transactionHistory: MutableList<ITransaction> = mutableListOf()

    // Bank Transfer
    fun addBankTransferTransaction(
            fromAccountHolder: Bank,
            toAccountHolder: Player
        ): ITransaction {

        transactionHistory.add(BankTransferTxn(fromAccountHolder, toAccountHolder))

        return transactionHistory.last()
    }

    // Build Shop
    fun addBuildShopTransaction(
            fromAccountHolder: Player,
            toAccountHolder: Bank,
            location: IBuildable,
            shopType: ShopType
        ): ITransaction {

        transactionHistory.add(BuildShopTxn(fromAccountHolder, toAccountHolder, shopType, location))

        return transactionHistory.last()
    }

    fun addRentPaymentTransaction(
            fromAccountHolder: Player,
            toAccountHolder: Player,
            location: IRentable
        ): ITransaction {

            transactionHistory.add(RentPaymentTxn(fromAccountHolder, toAccountHolder, location))

            return transactionHistory.last()
        }

    fun addFeeTransaction(
            fromAccountHolder: Bank,
            toAccountHolder: Player,
            location: IFeePayable
        ): ITransaction {

            transactionHistory.add(BankFeeTxn(fromAccountHolder, toAccountHolder, location))

            return transactionHistory.last()
        }

    fun addPurchaseTransaction(
            fromAccountHolder: Player,
            toAccountHolder: Bank,
            location: IPurchaseable
        ): ITransaction {

            transactionHistory.add(PurchaseLocationTxn(fromAccountHolder, toAccountHolder, location))

            return transactionHistory.last()
        }

}
