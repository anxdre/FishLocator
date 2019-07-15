@file:Suppress("SpellCheckingInspection")

package project.mapbox.cupang.util

import android.view.View
import kotlin.math.roundToInt

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun estTime(jarak: Int): String {
    val minute = jarak.toDouble() / 40 * 60
    val hours = minute / 60
    var convertedTime = "$minute menit"
    if (minute > 60) {
        convertedTime = "${hours.roundToInt()} jam ${(minute % 60).roundToInt()} menit"
    }
    return convertedTime
}