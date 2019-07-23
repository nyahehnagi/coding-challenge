package codingchallenge9

sealed class Money(_value: Int){
    abstract val value: Int
}

class GBP (_value:Int): Money(_value){
    override val value:Int
    init{
        // rules state that GBP cannot be negative
        value = if (_value >= 0) {_value} else - _value
    }

    override fun toString(): String {
        return "£$value"
    }

}