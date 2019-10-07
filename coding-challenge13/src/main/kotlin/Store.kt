package codingchallenge13

data class Store(val name: String, val postCode: String, val geoLocation: GeoLocation) {

    val storeName: String = name
    val storePostCode: String = postCode
    val storeGeoLocation: GeoLocation = geoLocation

    fun findClosestShop(storeList: List<Store>): Store {
        return storeList.fold(storeList[0], { closestStore: Store, element: Store ->
            when {
                this.geoLocation.haversine(element.geoLocation) < this.geoLocation.haversine(closestStore.geoLocation) -> element
                else -> closestStore
            }
        })
    }
}