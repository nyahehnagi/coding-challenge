package codingchallenge9

const val STARTING_BALANCE = 200

interface IAccountHolder {
    val name: String
}

interface ITransaction {
    val transactionAmount: Money
    val fromAccountHolder: IAccountHolder
    val toAccountHolder: IAccountHolder
}

class Bank : IAccountHolder {
    override val name = "bank"
}

class Player(_name: String) : IAccountHolder {
    override val name = _name //should add some logic here to not allow any reserved names e.g bank
}

class StartingBalanceBankTransferTxn(_fromAccountHolder: Bank, _toAccountHolder: Player) : ITransaction {
    override val transactionAmount: Money = GBP(STARTING_BALANCE)
    override val fromAccountHolder: IAccountHolder = _fromAccountHolder
    override val toAccountHolder: IAccountHolder = _toAccountHolder
}

class BankFeeTxn(_fromAccountHolder: Bank, _toAccountHolder: Player, location: IFeePayable) : ITransaction {

    override val fromAccountHolder: IAccountHolder = _fromAccountHolder
    override val toAccountHolder: IAccountHolder = _toAccountHolder
    override val transactionAmount: Money = location.fee
}

class RentPaymentTxn(_fromAccountHolder: Player, _toAccountHolder: Player, _location: IRentable) : ITransaction {

    override val fromAccountHolder: IAccountHolder = _fromAccountHolder
    override val toAccountHolder: IAccountHolder = _toAccountHolder
    override val transactionAmount: Money = _location.getRent()

}

class PurchaseLocationTxn(_fromAccountHolder: Player, _toAccountHolder: Bank, _location: IPurchaseable) : ITransaction {
    override val toAccountHolder: IAccountHolder = _toAccountHolder
    override val fromAccountHolder: IAccountHolder = _fromAccountHolder
    override val transactionAmount: Money = _location.purchasePrice
    val locationPurchased = _location
}

class BuildShopTxn(_fromAccountHolder: Player, _toAccountHolder: Bank, _shopType: ShopType, _location: IBuildable) :
    ITransaction {

    override val fromAccountHolder: IAccountHolder = _fromAccountHolder
    override val toAccountHolder: IAccountHolder = _toAccountHolder
    override val transactionAmount: Money = _location.getBuildCost(_shopType)
    val shopLocation: IBuildable = _location
    val shopType: ShopType = _shopType
}

