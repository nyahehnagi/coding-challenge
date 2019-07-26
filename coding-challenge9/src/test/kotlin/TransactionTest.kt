import codingchallenge9.*
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import java.lang.IllegalArgumentException
import java.lang.IndexOutOfBoundsException

class LocationTest {
    @Test
    fun `Should test that a retail site location that is undeveloped `(){
        val retailSiteLocation = RetailSite("Oxford Street", GBP(100),StoreBuildingCosts(GBP(10),GBP(20),GBP(30)), LocationRentalValues(GBP(40),GBP(50),GBP(60),GBP(70)), 1)
        //val retailSiteLocation = RetailSite("Oxford Street", 200)
        assertThat(retailSiteLocation.getRent().value, equalTo(40))
        assertThat(retailSiteLocation.name, equalTo("Oxford Street"))
        assertThat(retailSiteLocation.getRent().value, equalTo(40))
        assertThat(retailSiteLocation.isPurchaseable, equalTo(true))
    }


    @Test
    fun `Should test that a bank transfer transaction has been created`() {
        val ledger = Gameledger()
        val transactionType: TransactionType = TransactionType.BANKTRANSFER
        val player = Player("Bromley")
        val bank = Bank()


        val bankTransferTransaction : ITransaction = ledger.addTransaction(transactionType,bank,player)

        assertThat(bankTransferTransaction.fromAccountHolder.name, equalTo("bank"))
        assertThat(bankTransferTransaction.transactionAmount.value, equalTo(200))
        assertThat(bankTransferTransaction.toAccountHolder.name, equalTo("Bromley"))
    }

    @Test
    fun `Should test that a Location transaction for building a ministore has been created`() {
        val ledger = Gameledger()
        val transactionType: TransactionType = TransactionType.BUILDSHOP
        val fromPlayer = Player ("Kirsty")
        val bank = Bank()
        val shopType: ShopType = ShopType.MINISTORE
        val location  = RetailSite("Oxford Street", GBP(100),StoreBuildingCosts(GBP(10),GBP(20),GBP(30)), LocationRentalValues(GBP(40),GBP(50),GBP(60),GBP(70)), 1)



        val buildMinistoreTransaction : ITransaction = ledger.addTransaction(transactionType,fromPlayer,bank,location,shopType)

        assertThat(buildMinistoreTransaction.fromAccountHolder.name, equalTo("Kirsty"))
        assertThat(buildMinistoreTransaction.transactionAmount.value, equalTo(10))
        assertThat(buildMinistoreTransaction.toAccountHolder.name, equalTo("bank"))

        if (buildMinistoreTransaction is BuildShopTxn) {
            assertThat(buildMinistoreTransaction.shopLocation.name, equalTo("Oxford Street"))
            assertThat(buildMinistoreTransaction.shopType.name, equalTo("MINISTORE"))
        }
    }

    @Test
    fun `Should test that a rent transaction has been created for an undeveloped retail site`() {
        val ledger = Gameledger()
        val transactionType: TransactionType = TransactionType.RENTPAYMENT
        val playerFrom = Player("Bromley")
        val playerTo = Player("Kirsty")
        val location  = RetailSite("Oxford Street", GBP(100),StoreBuildingCosts(GBP(10),GBP(20),GBP(30)), LocationRentalValues(GBP(40),GBP(50),GBP(60),GBP(70)), 1)


        val rentPaymentTransaction : ITransaction = ledger.addTransaction(transactionType,playerFrom,playerTo,location)

        assertThat(rentPaymentTransaction.fromAccountHolder.name, equalTo("Bromley"))
        assertThat(rentPaymentTransaction.transactionAmount.value, equalTo(40))
        assertThat(rentPaymentTransaction.toAccountHolder.name, equalTo("Kirsty"))
    }

    @Test
    fun `Should test that a rent transaction has been created for an industrial site`() {
        val ledger = Gameledger()
        val transactionType: TransactionType = TransactionType.RENTPAYMENT
        val playerFrom = Player("Bromley")
        val playerTo = Player("Kirsty")
        val location = Industry("Magna Park")


        val rentPaymentTransaction : ITransaction = ledger.addTransaction(transactionType,playerFrom,playerTo,location)

        assertThat(rentPaymentTransaction.fromAccountHolder.name, equalTo("Bromley"))
        assertThat(rentPaymentTransaction.transactionAmount.value, equalTo(20))
        assertThat(rentPaymentTransaction.toAccountHolder.name, equalTo("Kirsty"))
    }

    @Test
    fun `Should test IllegalArgumentException raised as we cannot create a rent transaction for Free Parking Location`() {
        val ledger = Gameledger()
        val transactionType: TransactionType = TransactionType.RENTPAYMENT
        val playerFrom = Player("Bromley")
        val playerTo = Player("Kirsty")
        val location = FreeParking()

        val exception = assertThrows<IllegalArgumentException>{
            val rentPaymentTransaction: ITransaction = ledger.addTransaction(transactionType, playerFrom, playerTo, location)
        }
        assertThat(exception.message, equalTo("Location cannot be rented"))


    }

}



