package project.mapbox.cupang.util.locationutil

import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager

object RequestLocation {
    private var location: Location? = null
    fun getCurrentLocation(context: Context, locationListener: LocationListener): String {
        try {
            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if (gpsNetworkCheck(context)) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER , Timer.MIN_TIME_BW_UPDATES , Timer.MIN_DISTANCE_CHANGE_FOR_UPDATES.toFloat() , locationListener)
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            }
            if (gpsCheck(context)) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER , Timer.MIN_TIME_BW_UPDATES , Timer.MIN_DISTANCE_CHANGE_FOR_UPDATES.toFloat() , locationListener)
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            }
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
        return "&lat=${location?.latitude}&lon=${location?.longitude}"
    }
}