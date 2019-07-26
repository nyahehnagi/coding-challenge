import codingchallenge9.*
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import



import org.junit.Test

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
        val player: Player = Player("Bromley")
        val bank: Bank = Bank()


        val bankTransferTransaction : ITransaction = ledger.addTransaction(transactionType,bank,player)

        assertThat(bankTransferTransaction.fromAccountHolder.name, equalTo("bank"))
        assertThat(bankTransferTransaction.transactionAmount.value, equalTo(200))
        assertThat(bankTransferTransaction.toAccountHolder.name, equalTo("Bromley"))
    }

    @Test
    fun `Should test that a Location transaction for building a ministore has been created`() {
        val ledger = Gameledger()
        val transactionType: TransactionType = TransactionType.BUILDSHOP
        val fromPlayer: Player = Player ("Kirsty")
        val bank: Bank = Bank()
        val shopType: ShopType = ShopType.MINISTORE
        val location : RetailSite = RetailSite("Oxford Street", GBP(100),StoreBuildingCosts(GBP(10),GBP(20),GBP(30)), LocationRentalValues(GBP(40),GBP(50),GBP(60),GBP(70)), 1)



        val buildMinistoreTransaction : ITransaction = ledger.addTransaction(transactionType,fromPlayer,bank,location,shopType)

        assertThat(buildMinistoreTransaction.fromAccountHolder.name, equalTo("Kirsty"))
        assertThat(buildMinistoreTransaction.transactionAmount.value, equalTo(10))
        assertThat(buildMinistoreTransaction.toAccountHolder.name, equalTo("bank"))

        //assertThat(buildMinistoreTransaction is BuildShopTxn), equalTo(true)
        //assertThat(buildMinistoreTransaction is BuildShopTxn).isTrue()

        if (buildMinistoreTransaction is BuildShopTxn) {
            assertThat(buildMinistoreTransaction.shopLocation.name, equalTo("Oxford Street"))
            assertThat(buildMinistoreTransaction.shopType.name, equalTo("MINISTORE"))
        }
    }

    @Test
    fun `Should test that a rent transaction has been created for an undeveloped retail site`() {
        val ledger = Gameledger()
        val transactionType: TransactionType = TransactionType.RENTPAYMENT
        val playerFrom: Player = Player("Bromley")
        val playerTo: Player = Player("Kirsty")
        val location : RetailSite = RetailSite("Oxford Street", GBP(100),StoreBuildingCosts(GBP(10),GBP(20),GBP(30)), LocationRentalValues(GBP(40),GBP(50),GBP(60),GBP(70)), 1)


        val rentPaymentTransaction : ITransaction = ledger.addTransaction(transactionType,playerFrom,playerTo,location)

        assertThat(rentPaymentTransaction.fromAccountHolder.name, equalTo("Bromley"))
        assertThat(rentPaymentTransaction.transactionAmount.value, equalTo(40))
        assertThat(rentPaymentTransaction.toAccountHolder.name, equalTo("Kirsty"))
    }

    @Test
    fun `Should test that a rent transaction has been created for an industrial site`() {
        val ledger = Gameledger()
        val transactionType: TransactionType = TransactionType.RENTPAYMENT
        val playerFrom: Player = Player("Bromley")
        val playerTo: Player = Player("Kirsty")
        val location : Industry = Industry("Magna Park")


        val rentPaymentTransaction : ITransaction = ledger.addTransaction(transactionType,playerFrom,playerTo,location)

        assertThat(rentPaymentTransaction.fromAccountHolder.name, equalTo("Bromley"))
        assertThat(rentPaymentTransaction.transactionAmount.value, equalTo(20))
        assertThat(rentPaymentTransaction.toAccountHolder.name, equalTo("Kirsty"))
    }

    @Test
    fun `Should test that cannot create a rent transaction for Free Parking Location`() {
        val ledger = Gameledger()
        val transactionType: TransactionType = TransactionType.RENTPAYMENT
        val playerFrom: Player = Player("Bromley")
        val playerTo: Player = Player("Kirsty")
        val location : FreeParking = FreeParking()


        val rentPaymentTransaction : ITransaction = ledger.addTransaction(transactionType,playerFrom,playerTo,location)


        //assertThat(Exception.val, equalTo("Location cannot be rented"))

    }



}