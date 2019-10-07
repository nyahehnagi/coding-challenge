package codingchallenge13

import kotlin.math.*

data class GeoLocation(val lat: Double, val lon: Double) {
    companion object {
        const val earthRadiusM: Double = 6372800.0
    }

    val longitude: Double = lon
    val latitude: Double = lat

    //return distance in meters
    fun haversine(destination: GeoLocation): Double {
        val dLat = Math.toRadians(destination.latitude - this.latitude)
        val dLon = Math.toRadians(destination.longitude - this.longitude)
        val originLat = Math.toRadians(this.latitude)
        val destinationLat = Math.toRadians(destination.latitude)

        val a = sin(dLat / 2).pow(2.toDouble()) + sin(dLon / 2).pow(2.toDouble()) * cos(originLat) * cos(destinationLat);
        val c = 2 * asin(sqrt(a))
        return earthRadiusM * c
    }
}