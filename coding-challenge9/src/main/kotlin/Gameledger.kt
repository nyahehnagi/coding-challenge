package codingchallenge9


class Gameledger {

    private val transactionHistory: MutableList<ITransaction> = mutableListOf()
    // Bank Transfer
    fun addStartBalanceBankTransferTransaction(debitAccountHolder: Bank, creditAccountHolder: Player) {
        transactionHistory.add(StartingBalanceBankTransferTxn(debitAccountHolder, creditAccountHolder))
    }

    // Build Shop
    fun addBuildShopTransaction(
        debitAccountHolder: Player,
        creditAccountHolder: Bank,
        location: IBuildable,
        shopType: ShopType
    ) {
        transactionHistory.add(BuildShopTxn(debitAccountHolder, creditAccountHolder, shopType, location))
    }

    // Pay Rent
    fun addRentPaymentTransaction(debitAccountHolder: Player, creditAccountHolder: Player, location: IRentable) {
        transactionHistory.add(RentPaymentTxn(debitAccountHolder, creditAccountHolder, location))
    }

    // Pay Fee
    fun addFeeTransaction(debitAccountHolder: Bank, creditAccountHolder: Player, location: IFeePayable) {
        transactionHistory.add(BankFeeTxn(debitAccountHolder, creditAccountHolder, location))
    }

    // Purchase location
    fun addPurchaseTransaction(debitAccountHolder: Player, creditAccountHolder: Bank, location: IPurchaseable) {
        transactionHistory.add(PurchaseLocationTxn(debitAccountHolder, creditAccountHolder, location))
    }

    fun getPlayerBalance(player: Player): Money {
        //credits to player minus debits from the same player
        val balance: Int =
            transactionHistory.filter { it.creditAccountHolder.name == player.name }.sumBy { it.transactionAmount.value } -
                    transactionHistory.filter { it.debitAccountHolder.name == player.name }.sumBy { it.transactionAmount.value }
        return GBP(balance)
    }

    fun getLatestTransaction(): ITransaction {
        return transactionHistory.last()
    }
}
