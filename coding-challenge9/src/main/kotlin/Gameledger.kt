package codingchallenge9


class Gameledger {

    private val transactionHistory: MutableList<ITransaction> = mutableListOf()
    // Bank Transfer
    fun addStartBalanceBankTransferTransaction(bank: Bank, creditPlayer: Player) {
        transactionHistory.add(StartingBalanceBankTransferTxn(bank, creditPlayer))
    }

    // Build Shop
    fun addBuildShopTransaction(
        debitPlayer: Player,
        bank: Bank,
        location: IBuildable,
        shopType: ShopType
    ) {
        transactionHistory.add(BuildShopTxn(debitPlayer, bank, shopType, location))
    }

    // Pay Rent
    fun addRentPaymentTransaction(debitPlayer: Player, creditPlayer: Player, location: IRentable) {
        transactionHistory.add(RentPaymentTxn(debitPlayer, creditPlayer, location))
    }

    // Pay Fee
    fun addFeeTransaction(bank: Bank, creditPlayer: Player, location: IFeePayable) {
        transactionHistory.add(BankFeeTxn(bank, creditPlayer, location))
    }

    // Purchase location from Bank
    fun addLocationBankPurchaseTransaction(
        debitPlayer: Player,
        bank: Bank,
        location: IPurchaseable
    ) {

        transactionHistory.add(PurchaseLocationTxn(debitPlayer, bank, location, location.purchasePrice))
    }

    // Player to Player location Purchase
    fun addLocationPlayerToPlayerPurchaseTransaction(
        debitPlayer: Player,
        creditPlayer: Player,
        location: IPurchaseable,
        purchasePrice : Money
    ) {

        transactionHistory.add(PurchaseLocationTxn(debitPlayer, creditPlayer, location, purchasePrice))
    }

    fun addSellBuildingTransaction (){

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
                lastestPurchaseTxns.isEmpty()  -> listOf(element)
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
