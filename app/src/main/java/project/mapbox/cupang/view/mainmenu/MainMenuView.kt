package project.mapbox.cupang.view.mainmenu

import com.tomtom.online.sdk.common.location.LatLng
import com.tomtom.online.sdk.map.MarkerBuilder
import com.tomtom.online.sdk.map.SimpleMarkerBalloon
import project.mapbox.cupang.data.model.FishLocation

interface MainMenuView {
    fun attachData(mLocation : ArrayList<LatLng>, mBalloon:ArrayList<String>)
    fun showLoading()
    fun hideLoading()
    fun showUserLoc()
}