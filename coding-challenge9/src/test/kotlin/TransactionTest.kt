import codingchallenge9.*
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import java.lang.IllegalArgumentException


class LocationTest {
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
        val transactionType: TransactionType = TransactionType.BANKTRANSFER
        val player = Player("Bob")
        val bank = Bank()

        val bankTransferTransaction : ITransaction = ledger.addTransaction(transactionType,bank,player)

        assertThat(bankTransferTransaction.fromAccountHolder.name, equalTo("bank"))
        assertThat(bankTransferTransaction.transactionAmount.value, equalTo(200))
        assertThat(bankTransferTransaction.toAccountHolder.name, equalTo("Bob"))
    }

    @Test
    fun `Should test that a Location transaction for building a ministore has been created`() {
        val ledger = Gameledger()
        val transactionType: TransactionType = TransactionType.BUILDSHOP
        val fromPlayer = Player ("Jane")
        val bank = Bank()
        val shopType: ShopType = ShopType.MINISTORE
        val location  = RetailSite("Oxford Street", GBP(100),StoreBuildingCosts(GBP(10),GBP(20),GBP(30)), LocationRentalValues(GBP(40),GBP(50),GBP(60),GBP(70)), 1)

        val buildMinistoreTransaction : ITransaction = ledger.addTransaction(transactionType,fromPlayer,bank,location,shopType)

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
        val transactionType: TransactionType = TransactionType.RENTPAYMENT
        val playerFrom = Player("Bob")
        val playerTo = Player("Jane")
        val location  = RetailSite("Oxford Street", GBP(100),StoreBuildingCosts(GBP(10),GBP(20),GBP(30)), LocationRentalValues(GBP(40),GBP(50),GBP(60),GBP(70)), 1)

        val rentPaymentTransaction : ITransaction = ledger.addTransaction(transactionType,playerFrom,playerTo,location)

        assertThat(rentPaymentTransaction.fromAccountHolder.name, equalTo("Bob"))
        assertThat(rentPaymentTransaction.transactionAmount.value, equalTo(40))
        assertThat(rentPaymentTransaction.toAccountHolder.name, equalTo("Jane"))
    }

    @Test
    fun `Should test that a rent transaction has been created for a retail site with a ministore built on it`() {
        val ledger = Gameledger()
        val transactionType: TransactionType = TransactionType.RENTPAYMENT
        val playerFrom = Player("Bob")
        val playerTo = Player("Jane")
        val location  = RetailSite("Oxford Street", GBP(100),StoreBuildingCosts(GBP(10),GBP(20),GBP(30)), LocationRentalValues(GBP(40),GBP(50),GBP(60),GBP(70)), 1)
        location.buildMiniStore()

        val rentPaymentTransaction : ITransaction = ledger.addTransaction(transactionType,playerFrom,playerTo,location)

        assertThat(rentPaymentTransaction.fromAccountHolder.name, equalTo("Bob"))
        assertThat(rentPaymentTransaction.transactionAmount.value, equalTo(50))
        assertThat(rentPaymentTransaction.toAccountHolder.name, equalTo("Jane"))
    }

    @Test
    fun `Should test that a rent transaction has been created for an industrial site`() {
        val ledger = Gameledger()
        val transactionType: TransactionType = TransactionType.RENTPAYMENT
        val playerFrom = Player("Bob")
        val playerTo = Player("Jane")
        val location = Industry("Magna Park")

        val rentPaymentTransaction : ITransaction = ledger.addTransaction(transactionType,playerFrom,playerTo,location)

        assertThat(rentPaymentTransaction.fromAccountHolder.name, equalTo("Bob"))
        assertThat(rentPaymentTransaction.transactionAmount.value, equalTo(20))
        assertThat(rentPaymentTransaction.toAccountHolder.name, equalTo("Jane"))
    }

    @Test
    fun `Should test IllegalArgumentException raised as we cannot create a rent transaction for an invalid location`() {
        val ledger = Gameledger()
        val transactionType: TransactionType = TransactionType.RENTPAYMENT
        val playerFrom = Player("Bob")
        val playerTo = Player("Jane")
        val location = FreeParking()

        val exception = assertThrows<IllegalArgumentException>{
            ledger.addTransaction(transactionType, playerFrom, playerTo, location)
        }
        assertThat(exception.message, equalTo("Location cannot be rented"))
    }

    @Test
    fun `Should test IllegalArgumentException raised as we cannot create build shop transactions for any other transaction type bar BUILDSHOP`() {
        val ledger = Gameledger()
        val transactionType: TransactionType = TransactionType.RENTPAYMENT
        val fromPlayer = Player ("Jane")
        val bank = Bank()
        val shopType: ShopType = ShopType.MINISTORE
        val location  = RetailSite("Oxford Street", GBP(100),StoreBuildingCosts(GBP(10),GBP(20),GBP(30)), LocationRentalValues(GBP(40),GBP(50),GBP(60),GBP(70)), 1)

        val exception = assertThrows<IllegalArgumentException>{
            ledger.addTransaction(transactionType,fromPlayer,bank,location,shopType)
        }
        assertThat(exception.message, equalTo("Invalid Transaction Type for passed parameters"))
    }

    @Test
    fun `Should test that a fee transaction has been created`() {
        val ledger = Gameledger()
        val transactionType: TransactionType = TransactionType.BANKPAYMENT
        val bank = Bank()
        val playerTo = Player("Bob")
        val location  = Go()


        val rentPaymentTransaction : ITransaction = ledger.addTransaction(transactionType,bank,playerTo,location)

        assertThat(rentPaymentTransaction.fromAccountHolder.name, equalTo("bank"))
        assertThat(rentPaymentTransaction.transactionAmount.value, equalTo(100))
        assertThat(rentPaymentTransaction.toAccountHolder.name, equalTo("Bob"))
    }

    @Test
    fun `Should test IllegalArgumentException raised as we cannot create fee transactions for a location that does not support fees`() {
        val ledger = Gameledger()
        val transactionType: TransactionType = TransactionType.BANKPAYMENT
        val bank = Bank()
        val playerTo = Player("Bob")
        val location  = RetailSite("Oxford Street", GBP(100),StoreBuildingCosts(GBP(10),GBP(20),GBP(30)), LocationRentalValues(GBP(40),GBP(50),GBP(60),GBP(70)), 1)

        val exception = assertThrows<IllegalArgumentException>{
            ledger.addTransaction(transactionType,bank,playerTo,location)
        }
        assertThat(exception.message, equalTo("Location has no fee"))
    }

    @Test
    fun `Should test IllegalArgumentException raised as we cannot create fee or rent style transactions `() {
        val ledger = Gameledger()
        val transactionType: TransactionType = TransactionType.BANKTRANSFER
        val bank = Bank()
        val playerTo = Player("Bob")
        val location  = Go()

        val exception = assertThrows<IllegalArgumentException>{
            ledger.addTransaction(transactionType,bank,playerTo,location)
        }
        assertThat(exception.message, equalTo("Invalid Transaction Type for passed parameters"))
    }

    @Test
    fun `Should test that a purchase transaction has been created for purchase of an industrial site`() {
        val ledger = Gameledger()
        val transactionType: TransactionType = TransactionType.LOCATIONPURCHASE
        val playerFrom = Player("Bob")
        val playerTo = Bank()
        val location = Industry("Magna Park")

        val purchaseLocationTransaction : ITransaction = ledger.addTransaction(transactionType,playerFrom,playerTo,location)

        assertThat(purchaseLocationTransaction.fromAccountHolder.name, equalTo("Bob"))
        assertThat(purchaseLocationTransaction.transactionAmount.value, equalTo(100))
        assertThat(purchaseLocationTransaction.toAccountHolder.name, equalTo("bank"))
    }

    @Test
    fun `Should test that a purchase transaction has been created for purchase of a retail site`() {
        val ledger = Gameledger()
        val transactionType: TransactionType = TransactionType.LOCATIONPURCHASE
        val playerFrom = Player("Bob")
        val playerTo = Bank()
        val location  = RetailSite("Oxford Street", GBP(100),StoreBuildingCosts(GBP(10),GBP(20),GBP(30)), LocationRentalValues(GBP(40),GBP(50),GBP(60),GBP(70)), 1)

        val purchaseLocationTransaction : ITransaction = ledger.addTransaction(transactionType,playerFrom,playerTo,location)

        assertThat(purchaseLocationTransaction.fromAccountHolder.name, equalTo("Bob"))
        assertThat(purchaseLocationTransaction.transactionAmount.value, equalTo(100))
        assertThat(purchaseLocationTransaction.toAccountHolder.name, equalTo("bank"))

        assertThat((purchaseLocationTransaction as PurchaseLocationTxn).locationPurchased.name, equalTo("Oxford Street"))
    }

    @Test
    fun `Should test IllegalArgumentException raised for purchase of a non purchaseable location such as Free Parking`() {
        val ledger = Gameledger()
        val transactionType: TransactionType = TransactionType.LOCATIONPURCHASE
        val playerFrom = Player("Bob")
        val playerTo = Bank()
        val location = FreeParking()

        val exception = assertThrows<IllegalArgumentException>{
            ledger.addTransaction(transactionType, playerFrom, playerTo, location)
        }
        assertThat(exception.message, equalTo("Location cannot be purchased"))
    }

    @Test
    fun `Should test IllegalArgumentException raised for purchase between 2 players`() {
        val ledger = Gameledger()
        val transactionType: TransactionType = TransactionType.LOCATIONPURCHASE
        val playerFrom = Player("Bob")
        val playerTo = Player("Jane")
        val location = Industry("Magna Park")

        val exception = assertThrows<IllegalArgumentException>{
            ledger.addTransaction(transactionType, playerFrom, playerTo, location)
        }
        assertThat(exception.message, equalTo("Cannot purchase from this account type"))
    }

}



