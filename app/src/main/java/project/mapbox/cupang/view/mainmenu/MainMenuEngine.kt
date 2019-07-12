package project.mapbox.cupang.view.mainmenu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tomtom.online.sdk.common.location.LatLng
import com.tomtom.online.sdk.common.util.DistanceCalculator
import com.tomtom.online.sdk.map.*
import com.tomtom.online.sdk.map.model.MapTilesType
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import project.mapbox.cupang.R
import project.mapbox.cupang.data.Api.ApiFactory
import project.mapbox.cupang.util.invisible
import project.mapbox.cupang.util.visible


class MainMenuEngine : AppCompatActivity() , MainMenuView , OnMapReadyCallback {
    private var map: TomtomMap? = null
    private val mApi by lazy { ApiFactory() }
    private val mPresenter by lazy { MainMenuPresenter(this , mApi) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as MapFragment?
        mapFragment!!.getAsyncMap(this)
        hideLoading()
    }

    override fun onMapReady(tomtomMap: TomtomMap) {
        this.map = tomtomMap
        val mapPaddingVertical = resources.getDimension(R.dimen.map_padding_vertical).toDouble()
        val mapPaddingHorizontal = resources.getDimension(R.dimen.map_padding_horizontal).toDouble()

        tomtomMap.uiSettings.currentLocationView.show()
        tomtomMap.uiSettings.mapTilesType = MapTilesType.VECTOR
        tomtomMap.isMyLocationEnabled = true
        tomtomMap.setPadding(
                mapPaddingVertical , mapPaddingHorizontal ,
                mapPaddingVertical , mapPaddingHorizontal
        )
        tomtomMap.uiSettings.currentLocationView.setMargins(24,24,24,24)
        tomtomMap.uiSettings.compassView.setMargins(24,24,24,24)
        GlobalScope.launch(Dispatchers.Main) { mPresenter.initData(tomtomMap) }
    }

    override fun showLoading() {
        pb_main_menu.visible()
    }

    override fun hideLoading() {
        pb_main_menu.invisible()
    }

    override fun showUserLoc() {
        val lastUserLocation = map?.userLocation?.let { LatLng(it) }
        map?.centerOn(CameraPosition.builder(lastUserLocation).zoom(7.0).build())
    }

    override fun attachData(mLocation: ArrayList<LatLng> , mBalloon: ArrayList<String>) {
        showUserLoc()
        val lastUserLocation = map?.userLocation?.let { LatLng(it) }
        for (i in mLocation.indices) {
            map!!.addMarker(MarkerBuilder(mLocation[i]).icon(Icon.Factory.fromResources(this , R.drawable.fish)).markerBalloon(SimpleMarkerBalloon("${mBalloon[i]} \n\n " +
                    resources.getString(R.string.jarak_ke_lokasi) +
                    "${DistanceCalculator.calcDistInKilometers(lastUserLocation , mLocation[i]).toInt()}km")))
        }
    }

    public override
            /** @inheritDoc
             */
    fun onStart() {
        super.onStart()
        map_fragment.onStart()
    }

    public override
            /** @inheritDoc
             */
    fun onResume() {
        super.onResume()
        map_fragment.onResume()
    }

    public override
            /** @inheritDoc
             */
    fun onPause() {
        map_fragment.onPause()
//        callbackContainer.removeAllCallbacks()
        super.onPause()
    }

    public override fun onStop() {
        map_fragment.onStop()
        super.onStop()
    }

    public override fun onDestroy() {
        map_fragment.onDestroy()
        super.onDestroy()
    }

    override fun onLowMemory() {
        map_fragment.onLowMemory()
        super.onLowMemory()
    }
}