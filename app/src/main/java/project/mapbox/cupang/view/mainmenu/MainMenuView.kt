package project.mapbox.cupang.view.mainmenu

import com.tomtom.online.sdk.common.location.LatLng

interface MainMenuView {
    fun attachData(mLocation : ArrayList<LatLng>, mBalloon:ArrayList<String>)
    fun showLoading()
    fun hideLoading()
    fun showUserLoc()
    fun showNetworkError()
    fun showGpsError()
    fun getgpsPermission()
}