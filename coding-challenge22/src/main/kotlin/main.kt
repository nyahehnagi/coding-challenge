fun main() {
    var jlpLaptop : IJLPLaptop = JLPLaptop()
    jlpLaptop = ProcessorUpgrade(MemoryUpgrade(jlpLaptop))


    println("${laptop.description}")
    println("${laptop.totalPrice}")
}

interface IJLPLaptop {
    var description: String
    var totalPrice: Double
}

interface IJLPLaptopDecorator : IJLPLaptop {
    override var description: String
    override var totalPrice: Double

}

class JLPLaptop : IJLPLaptop {
    override var description: String = "Base Laptop @ £100"
    override var totalPrice: Double = 100.00
}

class ProcessorUpgrade(val jlpLaptop : IJLPLaptop): IJLPLaptopDecorator, IJLPLaptop by jlpLaptop {
    override var description : String = jlpLaptop.description + " , Processor Upgrade @ £200"
    override var totalPrice : Double = jlpLaptop.totalPrice + 200.00
}

class MemoryUpgrade(val jlpLaptop : IJLPLaptop): IJLPLaptopDecorator, IJLPLaptop by jlpLaptop {
    override var description : String = jlpLaptop.description + " , Memory Upgrade @ £100"
    override var totalPrice : Double = jlpLaptop.totalPrice + 100.00
}

class BatteryUpgrade(val jlpLaptop : IJLPLaptop): IJLPLaptopDecorator, IJLPLaptop by jlpLaptop {
    override var description : String = jlpLaptop.description + " , Battery Upgrade @ £50"
    override var totalPrice : Double = jlpLaptop.totalPrice + 50.00
}

class HardDriveUpgrade(val jlpLaptop : IJLPLaptop): IJLPLaptopDecorator, IJLPLaptop by jlpLaptop {
    override var description : String = jlpLaptop.description + " , Hard Drive Upgrade @ £75"
    override var totalPrice : Double = jlpLaptop.totalPrice + 75.00
}

class GraphicsUpgrade(val jlpLaptop : IJLPLaptop): IJLPLaptopDecorator, IJLPLaptop by jlpLaptop {
    override var description : String = jlpLaptop.description + " , Graphics Upgrade @ £300"
    override var totalPrice : Double = jlpLaptop.totalPrice + 300.00
}

class ShinyCaseUpgrade(val jlpLaptop : IJLPLaptop): IJLPLaptopDecorator, IJLPLaptop by jlpLaptop {
    override var description : String = jlpLaptop.description + " , Shiny Case @ £10"
    override var totalPrice : Double = jlpLaptop.totalPrice + 10.00
}