package project.mapbox.cupang.view.mainmenu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.afollestad.materialdialogs.MaterialDialog
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
import project.mapbox.cupang.util.estTime
import project.mapbox.cupang.util.invisible
import project.mapbox.cupang.util.visible


class MainMenuEngine : AppCompatActivity(), MainMenuView, OnMapReadyCallback {
    private var map: TomtomMap? = null
    private val mApi by lazy { ApiFactory() }
    private val mPresenter by lazy { MainMenuPresenter(this, mApi) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as MapFragment?
        mapFragment!!.getAsyncMap(this)
        btn_refresh.setOnClickListener { GlobalScope.launch(Dispatchers.Main) { mPresenter.initData(baseContext) } }
        hideLoading()
    }

    override fun onMapReady(mMap: TomtomMap) {
        this.map = mMap
        val mapPaddingVertical = resources.getDimension(R.dimen.map_padding_vertical).toDouble()
        val mapPaddingHorizontal = resources.getDimension(R.dimen.map_padding_horizontal).toDouble()

        mMap.uiSettings.currentLocationView.show()
        mMap.uiSettings.mapTilesType = MapTilesType.VECTOR
        mMap.isMyLocationEnabled = true
        mMap.setPadding(
            mapPaddingVertical, mapPaddingHorizontal,
            mapPaddingVertical, mapPaddingHorizontal
        )
        mMap.uiSettings.currentLocationView.setMargins(24, 24, 24, 24)
        mMap.uiSettings.compassView.setMargins(24, 24, 24, 24)
        GlobalScope.launch(Dispatchers.Main) { mPresenter.initData(baseContext) }
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

    override fun attachData(mLocation: ArrayList<LatLng>, mBalloon: ArrayList<String>) {
        showUserLoc()
        val lastUserLocation = map?.userLocation?.let { LatLng(it) }
        for (i in mLocation.indices) {
            map!!.addMarker(
                MarkerBuilder(mLocation[i]).icon(Icon.Factory.fromResources(this, R.drawable.fish)).markerBalloon(
                    SimpleMarkerBalloon(
                        "${mBalloon[i]} \n\n " +
                                resources.getString(R.string.jarak_ke_lokasi) +
                                "${DistanceCalculator.calcDistInKilometers(lastUserLocation, mLocation[i]).toInt()}km" +
                                "\n Waktu tempuh :${estTime(
                                    DistanceCalculator.calcDistInKilometers(
                                        lastUserLocation,
                                        mLocation[i]
                                    ).toInt()
                                )}\n" +
                                "\n Latitude : ${mLocation[i].latitude}" +
                                "\n Longitude : ${mLocation[i].longitude}"
                    )
                )
            )
        }
    }

    override fun showNetworkError() {
        MaterialDialog(this).show {
            title(R.string.network_error)
            message(R.string.network_error_sub)
            positiveButton(R.string.network_error_sub2) {
                GlobalScope.launch(Dispatchers.Main) { mPresenter.initData(baseContext) }
            }
        }
    }

    override fun getgpsPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 1)
    }

    override fun showGpsError() {
        MaterialDialog(this).show {
            title(R.string.gps_error)
            message(R.string.gps_error_sub)
            positiveButton(R.string.gps_error_sub2) {
                GlobalScope.launch(Dispatchers.Main) { mPresenter.initData(baseContext) }
                getgpsPermission()
            }
        }
    }

    public override fun onStart() {
        super.onStart()
        map_fragment.onStart()
    }

    public override fun onResume() {
        super.onResume()
        map_fragment.onResume()
    }

    public override fun onPause() {
        map_fragment.onPause()
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