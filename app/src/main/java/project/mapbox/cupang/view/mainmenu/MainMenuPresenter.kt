package project.mapbox.cupang.view.mainmenu

import android.content.Context
import com.tomtom.online.sdk.common.location.LatLng
import project.mapbox.cupang.data.Api.ApiRepository
import project.mapbox.cupang.util.gpsCheckPermission


class MainMenuPresenter(private val mView: MainMenuView, private val mApi: ApiRepository) {
    private val mLocation = ArrayList<LatLng>()
    private var mBalloon = ArrayList<String>()
    private var gpsPermission: Boolean = false

    suspend fun initData(context: Context) {
        gpsPermission = gpsCheckPermission(context)
        if (!gpsPermission) {
            mView.showGpsError()
        } else {
            mLocation.clear()
            mBalloon.clear()
            val mFishLocation = mApi.getDataAsync().await()
            if (mFishLocation.isNullOrEmpty()) {
                mView.showNetworkError()
            } else {
                for (i in mFishLocation.indices) {
                    mLocation.add(
                        LatLng(mFishLocation[i].latitude!!.toDouble(), mFishLocation[i].longtitude!!.toDouble())
                    )
                    mBalloon.add(
                        mFishLocation[i].deskripsi.toString()
                    )
                }
                mView.attachData(mLocation, mBalloon)
            }
        }
    }
}