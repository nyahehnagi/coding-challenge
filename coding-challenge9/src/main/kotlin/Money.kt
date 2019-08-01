package codingchallenge9

sealed class Money() {
    abstract val value: Int
}

class GBP(_value: Int) : Money() {
    override val value: Int

    init {
        // rules state that amount cannot be negative
        value = if (_value >= 0) {
            _value
        } else 0
    }

    override fun toString(): String {
        return "£$value"
    }
}