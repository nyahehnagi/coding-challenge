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
        ledger.addBankTransferTransaction(bank,player)

        assertThat(ledger.getLatestTransaction().fromAccountHolder.name, equalTo("bank"))
        assertThat(ledger.getLatestTransaction().transactionAmount.value, equalTo(200))
        assertThat(ledger.getLatestTransaction().toAccountHolder.name, equalTo("Bob"))
    }

    @Test
    fun `Should test that a Location transaction for building a ministore has been created`() {
        val ledger = Gameledger()
        val fromPlayer = Player ("Jane")
        val bank = Bank()
        val shopType: ShopType = ShopType.MINISTORE
        val location  = RetailSite("Oxford Street", GBP(100),StoreBuildingCosts(GBP(10),GBP(20),GBP(30)), LocationRentalValues(GBP(40),GBP(50),GBP(60),GBP(70)), 1)

        ledger.addBuildShopTransaction(fromPlayer,bank,location,shopType)

        val latestTransaction = ledger.getLatestTransaction()
        assertThat(latestTransaction.fromAccountHolder.name, equalTo("Jane"))
        assertThat(latestTransaction.transactionAmount.value, equalTo(10))
        assertThat(latestTransaction.toAccountHolder.name, equalTo("bank"))

        if (latestTransaction is BuildShopTxn) { //Could cast using 'as'
            assertThat(latestTransaction.shopLocation.name, equalTo("Oxford Street"))
            assertThat(latestTransaction.shopType.name, equalTo("MINISTORE"))
        }
    }

    @Test
    fun `Should test that a rent transaction has been created for an undeveloped retail site`() {
        val ledger = Gameledger()
        val playerFrom = Player("Bob")
        val playerTo = Player("Jane")
        val location  = RetailSite("Oxford Street", GBP(100),StoreBuildingCosts(GBP(10),GBP(20),GBP(30)), LocationRentalValues(GBP(40),GBP(50),GBP(60),GBP(70)), 1)

        ledger.addRentPaymentTransaction(playerFrom,playerTo,location)

        assertThat(ledger.getLatestTransaction().fromAccountHolder.name, equalTo("Bob"))
        assertThat(ledger.getLatestTransaction().transactionAmount.value, equalTo(40))
        assertThat(ledger.getLatestTransaction().toAccountHolder.name, equalTo("Jane"))
    }

    @Test
    fun `Should test that a rent transaction has been created for a retail site with a ministore built on it`() {
        val ledger = Gameledger()
        val playerFrom = Player("Bob")
        val playerTo = Player("Jane")
        val location  = RetailSite("Oxford Street", GBP(100),StoreBuildingCosts(GBP(10),GBP(20),GBP(30)), LocationRentalValues(GBP(40),GBP(50),GBP(60),GBP(70)), 1)
        location.buildMiniStore()

        ledger.addRentPaymentTransaction(playerFrom,playerTo,location)

        assertThat(ledger.getLatestTransaction().fromAccountHolder.name, equalTo("Bob"))
        assertThat(ledger.getLatestTransaction().transactionAmount.value, equalTo(50))
        assertThat(ledger.getLatestTransaction().toAccountHolder.name, equalTo("Jane"))
    }

    @Test
    fun `Should test that a rent transaction has been created for an industrial site`() {
        val ledger = Gameledger()
        val playerFrom = Player("Bob")
        val playerTo = Player("Jane")
        val location = Industry("Magna Park")

        ledger.addRentPaymentTransaction(playerFrom,playerTo,location)

        assertThat(ledger.getLatestTransaction().fromAccountHolder.name, equalTo("Bob"))
        assertThat(ledger.getLatestTransaction().transactionAmount.value, equalTo(20))
        assertThat(ledger.getLatestTransaction().toAccountHolder.name, equalTo("Jane"))
    }

    @Test
    fun `Should test that a fee transaction has been created`() {
        val ledger = Gameledger()
        val bank = Bank()
        val playerTo = Player("Bob")
        val location  = Go()
        ledger.addFeeTransaction(bank,playerTo,location)

        assertThat(ledger.getLatestTransaction().fromAccountHolder.name, equalTo("bank"))
        assertThat(ledger.getLatestTransaction().transactionAmount.value, equalTo(100))
        assertThat(ledger.getLatestTransaction().toAccountHolder.name, equalTo("Bob"))
    }

    @Test
    fun `Should test that a purchase transaction has been created for purchase of an industrial site`() {
        val ledger = Gameledger()
        val playerFrom = Player("Bob")
        val playerTo = Bank()
        val location = Industry("Magna Park")

        ledger.addPurchaseTransaction(playerFrom,playerTo,location)

        assertThat(ledger.getLatestTransaction().fromAccountHolder.name, equalTo("Bob"))
        assertThat(ledger.getLatestTransaction().transactionAmount.value, equalTo(100))
        assertThat(ledger.getLatestTransaction().toAccountHolder.name, equalTo("bank"))
    }

    @Test
    fun `Should test that a purchase transaction has been created for purchase of a retail site`() {
        val ledger = Gameledger()
        val playerFrom = Player("Bob")
        val playerTo = Bank()
        val location  = RetailSite("Oxford Street", GBP(100),StoreBuildingCosts(GBP(10),GBP(20),GBP(30)), LocationRentalValues(GBP(40),GBP(50),GBP(60),GBP(70)), 1)

        ledger.addPurchaseTransaction(playerFrom,playerTo,location)

        val latestTransaction = ledger.getLatestTransaction()
        assertThat(latestTransaction.fromAccountHolder.name, equalTo("Bob"))
        assertThat(latestTransaction.transactionAmount.value, equalTo(100))
        assertThat(latestTransaction.toAccountHolder.name, equalTo("bank"))

        assertThat((latestTransaction as PurchaseLocationTxn).locationPurchased.name, equalTo("Oxford Street"))
    }

    @Test
    fun `Should test the balance of player1 after a number of transactions`() {
        val ledger = Gameledger()
        val player1 = Player("Bob")
        val player2 = Player("Jane")
        val bank = Bank()
        val goLocation = Go()
        val industryLocation = Industry("Magna Park")
        val retailLocation = RetailSite(
            "Oxford Street",
            GBP(100),
            StoreBuildingCosts(GBP(10), GBP(20), GBP(30)),
            LocationRentalValues(GBP(40), GBP(50), GBP(60), GBP(70)),
            1
        )
        retailLocation.buildMiniStore()

        ledger.addFeeTransaction(bank, player1, goLocation) //add 200
        ledger.addBankTransferTransaction(bank, player1) // add 100
        ledger.addRentPaymentTransaction(player1, player2, retailLocation) // reduce 50
        ledger.addRentPaymentTransaction(player2,player1,industryLocation) //add 20

        assertThat(ledger.getPlayerBalance(player1).value, equalTo(270))
    }

}



