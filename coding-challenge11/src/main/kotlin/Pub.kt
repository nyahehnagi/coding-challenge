package cc11

data class Pubs(
    val Publist: MutableList<Pub>
)

data class Pub(
    val Name: String?,
    val PostCode: String?,
    val RegularBeers: MutableList<String>,
    val GuestBeers: MutableList<String>?,
    val PubService: String?,
    val Id: String?,
    val Branch: String?,
    val CreateTS: String?
)
