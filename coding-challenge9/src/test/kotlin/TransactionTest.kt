import codingchallenge9.*
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.Assert.assertNull

class TransactionTest {

    @Test
    fun `Should test that a retail site location that is undeveloped `() {
        val retailSiteLocation = RetailSite(
            "Oxford Street",
            Money(100),
            StoreBuildingCosts(Money(10), Money(20), Money(30)),
            LocationRentalValues(Money(40), Money(50), Money(60), Money(70)),
            1
        )
        assertThat(retailSiteLocation.getRent().value, equalTo(40))
        assertThat(retailSiteLocation.name, equalTo(retailSiteLocation.name))
        assertThat(retailSiteLocation.getRent().value, equalTo(40))
        assertThat(retailSiteLocation.purchasePrice.value, equalTo(100))
    }

    @Test
    fun `Should test that a bank transfer transaction has been created`() {

        val ledger = Gameledger()
        val player = Player("Bob")
        val bank = Bank()
        ledger.addStartBalanceBankTransferTransaction(bank, player)

        assertThat(ledger.getLatestTransaction().debitAccountHolder.name, equalTo(bank.name))
        assertThat(ledger.getLatestTransaction().transactionAmount.value, equalTo(200))
        assertThat(ledger.getLatestTransaction().creditAccountHolder.name, equalTo(player.name))
    }

    @Test
    fun `Should test that a Location transaction for building a ministore has been created`() {
        val ledger = Gameledger()
        val fromPlayer = Player("Jane")
        val bank = Bank()
        val shopType: ShopType = ShopType.MINISTORE
        val location = RetailSite(
            "Oxford Street",
            Money(100),
            StoreBuildingCosts(Money(10), Money(20), Money(30)),
            LocationRentalValues(Money(40), Money(50), Money(60), Money(70)),
            1
        )

        ledger.addBuildShopTransaction(fromPlayer, bank, location, shopType)

        val latestTransaction = ledger.getLatestTransaction()
        assertThat(latestTransaction.debitAccountHolder.name, equalTo(fromPlayer.name))
        assertThat(latestTransaction.transactionAmount.value, equalTo(10))
        assertThat(latestTransaction.creditAccountHolder.name, equalTo(bank.name))

        if (latestTransaction is BuildShopTxn) { //Could cast using 'as'
            assertThat(latestTransaction.shopLocation.name, equalTo(location.name))
            assertThat(latestTransaction.shopType.name, equalTo(ShopType.MINISTORE.name))
        }
    }

    @Test
    fun `Should test that a rent transaction has been created for an undeveloped retail site`() {
        val ledger = Gameledger()
        val playerFrom = Player("Bob")
        val playerTo = Player("Jane")
        val location = RetailSite(
            "Oxford Street",
            Money(100),
            StoreBuildingCosts(Money(10), Money(20), Money(30)),
            LocationRentalValues(Money(40), Money(50), Money(60), Money(70)),
            1
        )

        ledger.addRentPaymentTransaction(playerFrom, playerTo, location)

        assertThat(ledger.getLatestTransaction().debitAccountHolder.name, equalTo(playerFrom.name))
        assertThat(ledger.getLatestTransaction().transactionAmount.value, equalTo(40))
        assertThat(ledger.getLatestTransaction().creditAccountHolder.name, equalTo(playerTo.name))
    }

    @Test
    fun `Should test that a rent transaction has been created for a retail site with a ministore built on it`() {
        val ledger = Gameledger()
        val playerFrom = Player("Bob")
        val playerTo = Player("Jane")
        val location = RetailSite(
            "Oxford Street",
            Money(100),
            StoreBuildingCosts(Money(10), Money(20), Money(30)),
            LocationRentalValues(Money(40), Money(50), Money(60), Money(70)),
            1
        )
        location.buildMiniStore()

        ledger.addRentPaymentTransaction(playerFrom, playerTo, location)

        assertThat(ledger.getLatestTransaction().debitAccountHolder.name, equalTo(playerFrom.name))
        assertThat(ledger.getLatestTransaction().transactionAmount.value, equalTo(50))
        assertThat(ledger.getLatestTransaction().creditAccountHolder.name, equalTo("Jane"))
    }

    @Test
    fun `Should test that a rent transaction has been created for an industrial site`() {
        val ledger = Gameledger()
        val playerFrom = Player("Bob")
        val playerTo = Player("Jane")
        val location = Industry("Magna Park")

        ledger.addRentPaymentTransaction(playerFrom, playerTo, location)

        assertThat(ledger.getLatestTransaction().debitAccountHolder.name, equalTo(playerFrom.name))
        assertThat(ledger.getLatestTransaction().transactionAmount.value, equalTo(20))
        assertThat(ledger.getLatestTransaction().creditAccountHolder.name, equalTo(playerTo.name))
    }

    @Test
    fun `Should test that a fee transaction has been created`() {
        val ledger = Gameledger()
        val bank = Bank()
        val playerTo = Player("Bob")
        val location = Go()
        ledger.addFeeTransaction(bank, playerTo, location)

        assertThat(ledger.getLatestTransaction().debitAccountHolder.name, equalTo(bank.name))
        assertThat(ledger.getLatestTransaction().transactionAmount.value, equalTo(100))
        assertThat(ledger.getLatestTransaction().creditAccountHolder.name, equalTo(playerTo.name))
    }

    @Test
    fun `Should test that a purchase transaction has been created for purchase of an industrial site`() {
        val ledger = Gameledger()
        val playerFrom = Player("Bob")
        val bank = Bank()
        val location = Industry("Magna Park")

        ledger.addLocationBankPurchaseTransaction(playerFrom, bank, location)

        assertThat(ledger.getLatestTransaction().debitAccountHolder.name, equalTo(playerFrom.name))
        assertThat(ledger.getLatestTransaction().transactionAmount.value, equalTo(100))
        assertThat(ledger.getLatestTransaction().creditAccountHolder.name, equalTo(bank.name))
    }

    @Test
    fun `Should test that a purchase transaction has been created for purchase of a retail site`() {
        val ledger = Gameledger()
        val playerFrom = Player("Bob")
        val playerTo = Bank()
        val location = RetailSite(
            "Oxford Street",
            Money(100),
            StoreBuildingCosts(Money(10), Money(20), Money(30)),
            LocationRentalValues(Money(40), Money(50), Money(60), Money(70)),
            1
        )

        ledger.addLocationBankPurchaseTransaction(playerFrom, playerTo, location)

        val latestTransaction = ledger.getLatestTransaction()
        assertThat(latestTransaction.debitAccountHolder.name, equalTo(playerFrom.name))
        assertThat(latestTransaction.transactionAmount.value, equalTo(100))
        assertThat(latestTransaction.creditAccountHolder.name, equalTo(playerTo.name))

        assertThat((latestTransaction as PurchaseLocationTxn).locationPurchased.name, equalTo(location.name))
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
            Money(100),
            StoreBuildingCosts(Money(10), Money(20), Money(30)),
            LocationRentalValues(Money(40), Money(50), Money(60), Money(70)),
            1
        )
        retailLocation.buildMiniStore()

        ledger.addFeeTransaction(bank, player1, goLocation) //add 200
        ledger.addStartBalanceBankTransferTransaction(bank, player1) // add 100
        ledger.addLocationBankPurchaseTransaction(player1, bank, industryLocation) // deduct 100
        ledger.addRentPaymentTransaction(player1, player2, retailLocation) // deduct 50
        ledger.addRentPaymentTransaction(player2, player1, industryLocation) //add 20

        assertThat(ledger.getPlayerBalance(player1).amount.value, equalTo(170))
        assertThat(ledger.getPlayerBalance(player1).isCredit, equalTo(true))
    }

    @Test
    fun `Should test the balance of player1 after a number of transactions is a debit`() {
        val ledger = Gameledger()
        val player1 = Player("Bob")
        val player2 = Player("Jane")
        val bank = Bank()
        val industryLocation = Industry("Magna Park")
        val retailLocation = RetailSite(
            "Oxford Street",
            Money(100),
            StoreBuildingCosts(Money(10), Money(20), Money(30)),
            LocationRentalValues(Money(40), Money(50), Money(60), Money(70)),
            1
        )
        retailLocation.buildMiniStore()

        ledger.addLocationBankPurchaseTransaction(player1, bank, industryLocation) // deduct 100
        ledger.addRentPaymentTransaction(player1, player2, retailLocation) // deduct 50

        assertThat(ledger.getPlayerBalance(player1).amount.value, equalTo(150))
        assertThat(ledger.getPlayerBalance(player1).isCredit, equalTo(false))
    }

    @Test
    fun `Should test the balance of player1 is zero if no transactions have been created`() {
        val ledger = Gameledger()
        val player1 = Player("Bob")

        assertThat(ledger.getPlayerBalance(player1).amount.value, equalTo(0))
        assertThat(ledger.getPlayerBalance(player1).isCredit, equalTo(true))

    }

    @Test
    fun `Should test a list of owned locations are returned`() {
        val ledger = Gameledger()
        val player1 = Player("Bob")
        val bank = Bank()
        val industryLocation = Industry("Magna Park")
        val retailLocation = RetailSite(
            "Oxford Street",
            Money(100),
            StoreBuildingCosts(Money(10), Money(20), Money(30)),
            LocationRentalValues(Money(40), Money(50), Money(60), Money(70)),
            1
        )
        retailLocation.buildMiniStore()

        ledger.addLocationBankPurchaseTransaction(player1, bank, industryLocation) // deduct 100
        ledger.addLocationBankPurchaseTransaction(player1, bank, retailLocation)

        val player1Locations = ledger.getLocationsOwnedByPlayer(player1)

        assertThat(player1Locations.locations.size, equalTo(2))
        assertThat(player1Locations.locations[0].name, equalTo(retailLocation.name))
        assertThat(player1Locations.locations[1].name, equalTo(industryLocation.name))
    }

    @Test
    fun `Should test a list of owned locations are returned after all location has been sold on and then back to the original owner`() {
        val ledger = Gameledger()
        val player1 = Player("Bob")
        val player2 = Player("Jane")
        val bank = Bank()
        val industryLocation = Industry("Magna Park")
        val retailLocation = RetailSite(
            "Oxford Street",
            Money(100),
            StoreBuildingCosts(Money(10), Money(20), Money(30)),
            LocationRentalValues(Money(40), Money(50), Money(60), Money(70)),
            1
        )
        retailLocation.buildMiniStore()

        ledger.addLocationBankPurchaseTransaction(player1, bank, industryLocation)
        ledger.addLocationBankPurchaseTransaction(player1, bank, retailLocation)
        ledger.addLocationPlayerToPlayerPurchaseTransaction(
            player2,
            player1,
            industryLocation,
            industryLocation.purchasePrice
        )
        ledger.addLocationPlayerToPlayerPurchaseTransaction(
            player2,
            player1,
            retailLocation,
            retailLocation.purchasePrice
        )
        ledger.addLocationPlayerToPlayerPurchaseTransaction(
            player1,
            player2,
            industryLocation,
            industryLocation.purchasePrice
        )
        ledger.addLocationPlayerToPlayerPurchaseTransaction(
            player1,
            player2,
            retailLocation,
            retailLocation.purchasePrice
        )

        val player1Locations = ledger.getLocationsOwnedByPlayer(player1)

        assertThat(player1Locations.locations.size, equalTo(2))
        assertThat(player1Locations.locations[0].name, equalTo(retailLocation.name))
        assertThat(player1Locations.locations[1].name, equalTo(industryLocation.name))
    }


    @Test
    fun `Should test who the correct owner of a location is`() {
        val ledger = Gameledger()
        val player1 = Player("Bob")
        val player2 = Player("Jane")
        val bank = Bank()
        val industryLocation = Industry("Magna Park")
        val retailLocation = RetailSite(
            "Oxford Street",
            Money(100),
            StoreBuildingCosts(Money(10), Money(20), Money(30)),
            LocationRentalValues(Money(40), Money(50), Money(60), Money(70)),
            1
        )
        retailLocation.buildMiniStore()

        ledger.addLocationBankPurchaseTransaction(player1, bank, industryLocation)
        ledger.addLocationPlayerToPlayerPurchaseTransaction(
            player2,
            player1,
            industryLocation,
            industryLocation.purchasePrice
        )

        val playerIndustry = ledger.getOwnerOfLocation(industryLocation)
        val playerRetail = ledger.getOwnerOfLocation(retailLocation)


        assertThat(playerIndustry?.name, equalTo(player2.name))
        assertNull("Verify object is null", playerRetail)

    }

}



