import codingchallenge9.*
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class TransactionTest {
    @Test
    fun `Should test that a retail site location that is undeveloped `(){
        val retailSiteLocation = RetailSite("Oxford Street", GBP(100),StoreBuildingCosts(GBP(10),GBP(20),GBP(30)), LocationRentalValues(GBP(40),GBP(50),GBP(60),GBP(70)), 1)
        assertThat(retailSiteLocation.getRent().value, equalTo(40))
        assertThat(retailSiteLocation.name, equalTo("Oxford Street"))
        assertThat(retailSiteLocation.getRent().value, equalTo(40))
        assertThat(retailSiteLocation.purchasePrice.value, equalTo(100))
    }

    @Test
    fun `Should test that a bank transfer transaction has been created`() {
        val ledger = Gameledger()
        val player = Player("Bob")
        val bank = Bank()

        val bankTransferTransaction : ITransaction = ledger.addBankTransferTransaction(bank,player)

        assertThat(bankTransferTransaction.fromAccountHolder.name, equalTo("bank"))
        assertThat(bankTransferTransaction.transactionAmount.value, equalTo(200))
        assertThat(bankTransferTransaction.toAccountHolder.name, equalTo("Bob"))
    }

    @Test
    fun `Should test that a Location transaction for building a ministore has been created`() {
        val ledger = Gameledger()
        val fromPlayer = Player ("Jane")
        val bank = Bank()
        val shopType: ShopType = ShopType.MINISTORE
        val location  = RetailSite("Oxford Street", GBP(100),StoreBuildingCosts(GBP(10),GBP(20),GBP(30)), LocationRentalValues(GBP(40),GBP(50),GBP(60),GBP(70)), 1)

        val buildMinistoreTransaction : ITransaction = ledger.addBuildShopTransaction(fromPlayer,bank,location,shopType)

        assertThat(buildMinistoreTransaction.fromAccountHolder.name, equalTo("Jane"))
        assertThat(buildMinistoreTransaction.transactionAmount.value, equalTo(10))
        assertThat(buildMinistoreTransaction.toAccountHolder.name, equalTo("bank"))

        if (buildMinistoreTransaction is BuildShopTxn) { //Could cast using 'as'
            assertThat(buildMinistoreTransaction.shopLocation.name, equalTo("Oxford Street"))
            assertThat(buildMinistoreTransaction.shopType.name, equalTo("MINISTORE"))
        }
    }

    @Test
    fun `Should test that a rent transaction has been created for an undeveloped retail site`() {
        val ledger = Gameledger()
        val playerFrom = Player("Bob")
        val playerTo = Player("Jane")
        val location  = RetailSite("Oxford Street", GBP(100),StoreBuildingCosts(GBP(10),GBP(20),GBP(30)), LocationRentalValues(GBP(40),GBP(50),GBP(60),GBP(70)), 1)

        val rentPaymentTransaction : ITransaction = ledger.addRentPaymentTransaction(playerFrom,playerTo,location)

        assertThat(rentPaymentTransaction.fromAccountHolder.name, equalTo("Bob"))
        assertThat(rentPaymentTransaction.transactionAmount.value, equalTo(40))
        assertThat(rentPaymentTransaction.toAccountHolder.name, equalTo("Jane"))
    }

    @Test
    fun `Should test that a rent transaction has been created for a retail site with a ministore built on it`() {
        val ledger = Gameledger()
        val playerFrom = Player("Bob")
        val playerTo = Player("Jane")
        val location  = RetailSite("Oxford Street", GBP(100),StoreBuildingCosts(GBP(10),GBP(20),GBP(30)), LocationRentalValues(GBP(40),GBP(50),GBP(60),GBP(70)), 1)
        location.buildMiniStore()

        val rentPaymentTransaction : ITransaction = ledger.addRentPaymentTransaction(playerFrom,playerTo,location)

        assertThat(rentPaymentTransaction.fromAccountHolder.name, equalTo("Bob"))
        assertThat(rentPaymentTransaction.transactionAmount.value, equalTo(50))
        assertThat(rentPaymentTransaction.toAccountHolder.name, equalTo("Jane"))
    }

    @Test
    fun `Should test that a rent transaction has been created for an industrial site`() {
        val ledger = Gameledger()
        val playerFrom = Player("Bob")
        val playerTo = Player("Jane")
        val location = Industry("Magna Park")

        val rentPaymentTransaction : ITransaction = ledger.addRentPaymentTransaction(playerFrom,playerTo,location)

        assertThat(rentPaymentTransaction.fromAccountHolder.name, equalTo("Bob"))
        assertThat(rentPaymentTransaction.transactionAmount.value, equalTo(20))
        assertThat(rentPaymentTransaction.toAccountHolder.name, equalTo("Jane"))
    }

    @Test
    fun `Should test that a fee transaction has been created`() {
        val ledger = Gameledger()
        val bank = Bank()
        val playerTo = Player("Bob")
        val location  = Go()
        val rentPaymentTransaction : ITransaction = ledger.addFeeTransaction(bank,playerTo,location)

        assertThat(rentPaymentTransaction.fromAccountHolder.name, equalTo("bank"))
        assertThat(rentPaymentTransaction.transactionAmount.value, equalTo(100))
        assertThat(rentPaymentTransaction.toAccountHolder.name, equalTo("Bob"))
    }

    @Test
    fun `Should test that a purchase transaction has been created for purchase of an industrial site`() {
        val ledger = Gameledger()
        val playerFrom = Player("Bob")
        val playerTo = Bank()
        val location = Industry("Magna Park")

        val purchaseLocationTransaction : ITransaction = ledger.addPurchaseTransaction(playerFrom,playerTo,location)

        assertThat(purchaseLocationTransaction.fromAccountHolder.name, equalTo("Bob"))
        assertThat(purchaseLocationTransaction.transactionAmount.value, equalTo(100))
        assertThat(purchaseLocationTransaction.toAccountHolder.name, equalTo("bank"))
    }

    @Test
    fun `Should test that a purchase transaction has been created for purchase of a retail site`() {
        val ledger = Gameledger()
        val playerFrom = Player("Bob")
        val playerTo = Bank()
        val location  = RetailSite("Oxford Street", GBP(100),StoreBuildingCosts(GBP(10),GBP(20),GBP(30)), LocationRentalValues(GBP(40),GBP(50),GBP(60),GBP(70)), 1)

        val purchaseLocationTransaction : ITransaction = ledger.addPurchaseTransaction(playerFrom,playerTo,location)

        assertThat(purchaseLocationTransaction.fromAccountHolder.name, equalTo("Bob"))
        assertThat(purchaseLocationTransaction.transactionAmount.value, equalTo(100))
        assertThat(purchaseLocationTransaction.toAccountHolder.name, equalTo("bank"))

        assertThat((purchaseLocationTransaction as PurchaseLocationTxn).locationPurchased.name, equalTo("Oxford Street"))
    }
}



