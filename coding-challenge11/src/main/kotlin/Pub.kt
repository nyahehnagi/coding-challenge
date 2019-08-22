package cc11

enum class BeerType{
    REGULARBEER,
    GUESTBEER
}

data class Pub(
    val name: String,
    val postCode: String,
    val regularBeers: List<String>,
    val guestBeers: List<String>,
    val pubService: String,
    val id: String,
    val branch: String,
    val createTS: String
)

data class Beer(
    val beerName: String,
    val pubName: String,
    val pubService:String,
    val beerType: BeerType

)
