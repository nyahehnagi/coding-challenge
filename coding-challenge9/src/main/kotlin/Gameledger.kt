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
    fun addLocationPurchaseTransaction(
        debitAccountHolder: IAccountHolder,
        creditAccountHolder: IAccountHolder,
        location: IPurchaseable,
        purchasePrice: Money
    ) {
        transactionHistory.add(PurchaseLocationTxn(debitAccountHolder, creditAccountHolder, location, purchasePrice))
    }


    fun getPlayerBalance(player: Player): Balance {
        //total credits to player minus total debits from the same player
        val balance: Int =
            transactionHistory.filter { it.creditAccountHolder.name == player.name }.sumBy { it.transactionAmount.value } -
                    transactionHistory.filter { it.debitAccountHolder.name == player.name }.sumBy { it.transactionAmount.value }


        return Balance(balance)
    }

    fun getLatestTransaction(): ITransaction {
        return transactionHistory.last()
    }

    fun getLocationsOwnedByPlayer(player: Player): LocationList {

        val result = getLatestLocationTransactionsForEachLocation().filter { it.debitAccountHolder.name == player.name }

        return LocationList(result.map { it.locationPurchased }.toList())
    }

    private fun getLatestLocationTransactionsForEachLocation() : List<PurchaseLocationTxn>{
        val purchaseTransactions = transactionHistory.filterIsInstance<PurchaseLocationTxn>()

        return purchaseTransactions.foldRight(emptyList()) { element, lastestPurchaseTxns ->
            when {
                lastestPurchaseTxns == emptyList<PurchaseLocationTxn>() -> listOf(element)
                lastestPurchaseTxns.any { it.locationPurchased.name == element.locationPurchased.name } -> lastestPurchaseTxns
                else -> lastestPurchaseTxns + listOf(element)
            }
        }
    }

    fun getOwnerOfLocation(location: ILocation): IAccountHolder? {

        val indexOfLatest =
            transactionHistory.filterIsInstance<PurchaseLocationTxn>().indexOfLast { it.locationPurchased == location }
        return if (indexOfLatest == -1)
            null
        else
            transactionHistory[indexOfLatest].debitAccountHolder
    }

}
