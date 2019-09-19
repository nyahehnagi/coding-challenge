package codingchallenge9

const val STARTING_BALANCE = 200

interface ITransaction {
    val transactionAmount: Money
    val debitAccountHolder: IAccountHolder
    val creditAccountHolder: IAccountHolder
}

class StartingBalanceBankTransferTxn(_debitAccountHolder: Bank, _creditAccountHolder: Player) : ITransaction {
    override val transactionAmount: Money = Money(STARTING_BALANCE)
    override val debitAccountHolder: IAccountHolder = _debitAccountHolder
    override val creditAccountHolder: IAccountHolder = _creditAccountHolder
}

class BankFeeTxn(_debitAccountHolder: Bank, _creditAccountHolder: Player, location: IFeePayable) : ITransaction {

    override val debitAccountHolder: IAccountHolder = _debitAccountHolder
    override val creditAccountHolder: IAccountHolder = _creditAccountHolder
    override val transactionAmount: Money = location.fee
}

class RentPaymentTxn(_debitAccountHolder: Player, _creditAccountHolder: Player, _location: IRentable) : ITransaction {

    override val debitAccountHolder: IAccountHolder = _debitAccountHolder
    override val creditAccountHolder: IAccountHolder = _creditAccountHolder
    override val transactionAmount: Money = _location.getRent()

}

class PurchaseLocationTxn(
    _debitAccountHolder: IAccountHolder,
    _creditAccountHolder: IAccountHolder,
    _location: IPurchaseable,
    _transactionAmount: Money
) : ITransaction {
    override val creditAccountHolder: IAccountHolder = _creditAccountHolder
    override val debitAccountHolder: IAccountHolder = _debitAccountHolder
    override val transactionAmount: Money = _transactionAmount
    val locationPurchased = _location
}

class BuildShopTxn(
    _debitAccountHolder: Player,
    _creditAccountHolder: Bank,
    _shopType: ShopType,
    _location: IBuildable
) :
    ITransaction {

    override val debitAccountHolder: IAccountHolder = _debitAccountHolder
    override val creditAccountHolder: IAccountHolder = _creditAccountHolder
    override val transactionAmount: Money = _location.getBuildCost(_shopType)
    val shopLocation: IBuildable = _location
    val shopType: ShopType = _shopType
}

