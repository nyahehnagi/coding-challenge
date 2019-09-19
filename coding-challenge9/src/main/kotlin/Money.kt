package codingchallenge9
/*
interface Money {
    abstract val value: Int
    abstract val isCredit: Boolean
    operator fun plus(other:Money): Money
    operator fun minus(other:Money) :Money
}
*/
class Money(_value: Int)  {

    val value: Int

    //Always positive
    init {
        value = if (_value >= 0) {
            _value
        } else {
            -_value
        }
    }

    override fun toString(): String {
        return "£$value"
    }

    //operator  fun plus (other: Money) : Money = Money(value + other.value)

    //operator fun minus (other: Money) : Money = Money(value - other.value)
}

class Balance (_value: Int){
    val isCredit: Boolean
    val amount: Money

    init {
        amount = Money(_value)
        isCredit = _value >= 0
    }
}