package codingchallenge13

data class GeoLocation(val lat: Double, val lon: Double) {
    companion object {
        const val earthRadiusM: Double = 6372800.0
    }

    val longitude: Double = lon
    val latitide: Double = lat

    //return distance in meters
    fun haversine(destination: GeoLocation): Double {
        val dLat = Math.toRadians(destination.latitide - this.latitide)
        val dLon = Math.toRadians(destination.longitude - this.longitude)
        val originLat = Math.toRadians(this.latitide)
        val destinationLat = Math.toRadians(destination.latitide)

        val a = Math.pow(Math.sin(dLat / 2), 2.toDouble()) + Math.pow(
            Math.sin(dLon / 2),
            2.toDouble()
        ) * Math.cos(originLat) * Math.cos(destinationLat);
        val c = 2 * Math.asin(Math.sqrt(a))
        return earthRadiusM * c
    }
}