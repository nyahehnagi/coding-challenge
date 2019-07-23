package codingchallenge9

class Gameledger {
    private val transactionHistory: MutableList<ITransaction> = mutableListOf()

    fun addTransaction (transactionType : TransactionType,
                        transactionData : TransactionData){
        val transactionFactory  = TransactionFactory()
        transactionHistory.add(transactionFactory.createTransaction(transactionType,transactionData ))
    }
}
