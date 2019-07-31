package codingchallenge9


class Gameledger {

    private val transactionHistory: MutableList<ITransaction> = mutableListOf()

    // Bank Transfer
    fun addBankTransferTransaction( fromAccountHolder: Bank, toAccountHolder: Player) {
        transactionHistory.add(BankTransferTxn(fromAccountHolder, toAccountHolder))
    }

    // Build Shop
    fun addBuildShopTransaction(fromAccountHolder: Player, toAccountHolder: Bank, location: IBuildable, shopType: ShopType) {
        transactionHistory.add(BuildShopTxn(fromAccountHolder, toAccountHolder, shopType, location))
    }

    fun addRentPaymentTransaction(fromAccountHolder: Player, toAccountHolder: Player, location: IRentable) {
            transactionHistory.add(RentPaymentTxn(fromAccountHolder, toAccountHolder, location))
        }

    fun addFeeTransaction(fromAccountHolder: Bank, toAccountHolder: Player, location: IFeePayable) {
            transactionHistory.add(BankFeeTxn(fromAccountHolder, toAccountHolder, location))
        }

    fun addPurchaseTransaction(fromAccountHolder: Player, toAccountHolder: Bank, location: IPurchaseable) {
            transactionHistory.add(PurchaseLocationTxn(fromAccountHolder, toAccountHolder, location))
        }

    fun getPlayerBalance(player: Player):Money {
        val balance : Int  = transactionHistory.filter { it.toAccountHolder.name == player.name }.sumBy { it.transactionAmount.value } -
                transactionHistory.filter { it.fromAccountHolder.name == player.name }.sumBy { it.transactionAmount.value }
        return GBP (balance)
    }

    fun getLatestTransaction () : ITransaction{
        return transactionHistory.last()
    }
}
