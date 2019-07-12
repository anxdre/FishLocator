package project.mapbox.cupang.util.locationutil

object Timer {
    const val MIN_DISTANCE_CHANGE_FOR_UPDATES: Int = 10
    const val MIN_TIME_BW_UPDATES: Long = 1000 * 60 * 1
    const val DEFAULT_INTERVAL_IN_MILLISECONDS = 1000L
    const val DEFAULT_MAX_WAIT_TIME = DEFAULT_INTERVAL_IN_MILLISECONDS * 5
}