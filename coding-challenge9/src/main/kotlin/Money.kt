package codingchallenge9

sealed class Money {
    abstract val value: Int
    abstract val isCredit: Boolean
}

class GBP(_value: Int) : Money() {
    override val isCredit: Boolean
    override val value: Int

    init {
        if (_value >= 0) {
            value = _value
            isCredit = true
        } else {
            value = -_value
            isCredit = false
        }
    }

    override fun toString(): String {
        return "£$value"
    }
}