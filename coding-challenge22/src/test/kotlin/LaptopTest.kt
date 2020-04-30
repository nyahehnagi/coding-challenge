
package codingchallenge22

import org.hamcrest.CoreMatchers.equalTo
import org.junit.Test
import org.hamcrest.MatcherAssert.assertThat

class LaptopTest {

    @Test
    fun `Should test that a basic laptop is created`() {
        val jlpLaptop: IJLPLaptop = JLPLaptop()

        assertThat(jlpLaptop.description, equalTo("Base Laptop @ £100"))
        assertThat(jlpLaptop.totalPrice, equalTo(100.00))
    }

    @Test
    fun `Should test that a laptop with Memory Upgrade is created`() {
        val jlpLaptop: IJLPLaptop =MemoryUpgrade(JLPLaptop())

        assertThat(jlpLaptop.description, equalTo("Base Laptop @ £100 , Memory Upgrade @ £100"))
        assertThat(jlpLaptop.totalPrice, equalTo(200.00))
    }

    @Test

    fun `Should test that a laptop with Memory and Processor Upgrade is created`() {
        val jlpLaptop: IJLPLaptop =ProcessorUpgrade(MemoryUpgrade(JLPLaptop()))

        assertThat(jlpLaptop.description, equalTo("Base Laptop @ £100 , Memory Upgrade @ £100 , Processor Upgrade @ £200"))
        assertThat(jlpLaptop.totalPrice, equalTo(400.00))
    }

    @Test
    fun `Should test that a laptop with Memory,  Processor, Battery Upgrade is created`() {
        val jlpLaptop: IJLPLaptop =BatteryUpgrade(ProcessorUpgrade(MemoryUpgrade(JLPLaptop())))

        assertThat(jlpLaptop.description, equalTo("Base Laptop @ £100 , Memory Upgrade @ £100 , Processor Upgrade @ £200 , Battery Upgrade @ £50"))
        assertThat(jlpLaptop.totalPrice, equalTo(450.00))
    }

    @Test
    fun `Should test that a laptop with Graphics Upgrade is created`() {
        val jlpLaptop: IJLPLaptop =GraphicsUpgrade(JLPLaptop())

        assertThat(jlpLaptop.description, equalTo("Base Laptop @ £100 , Graphics Upgrade @ £300"))
        assertThat(jlpLaptop.totalPrice, equalTo(400.00))
    }

    @Test
    fun `Should test that a laptop with a Shiny Case is created`() {
        val jlpLaptop: IJLPLaptop =ShinyCaseUpgrade(JLPLaptop())

        assertThat(jlpLaptop.description, equalTo("Base Laptop @ £100 , Shiny Case @ £10"))
        assertThat(jlpLaptop.totalPrice, equalTo(110.00))
    }

    @Test
    fun `Should test that a laptop with HardDrive Upgrade is created`() {
        val jlpLaptop: IJLPLaptop =HardDriveUpgrade(JLPLaptop())

        assertThat(jlpLaptop.description, equalTo("Base Laptop @ £100 , Hard Drive Upgrade @ £75"))
        assertThat(jlpLaptop.totalPrice, equalTo(175.00))
    }
}