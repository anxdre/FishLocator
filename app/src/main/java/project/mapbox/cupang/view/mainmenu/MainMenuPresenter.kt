package project.mapbox.cupang.view.mainmenu

import android.content.Context
import com.tomtom.online.sdk.common.location.LatLng
import project.mapbox.cupang.data.Api.ApiRepository
import project.mapbox.cupang.data.Database.DbModule
import project.mapbox.cupang.data.model.OfflineLocation
import project.mapbox.cupang.util.checkNetworkState
import project.mapbox.cupang.util.gpsCheckPermission


class MainMenuPresenter(private val mView: MainMenuView, private val mApi: ApiRepository) {
    private var mLocation = ArrayList<LatLng>()
    private var mBalloon = ArrayList<String>()
    private var gpsPermission: Boolean = false

    suspend fun initData(context: Context) {
        gpsPermission = gpsCheckPermission(context)
        if (!gpsPermission) {
            mView.showGpsError()
        } else if (!checkNetworkState(context)) {
            getDataFromDatabase()
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
                saveDataToDatabase()
                mView.attachData(mLocation, mBalloon)
            }
        }
    }

    private fun saveDataToDatabase() {
        DbModule.RoomDatabase?.offlineDao?.deleteAll()
        for (i in mLocation.indices) {
            val mOfflineLocation = OfflineLocation(
                i,
                mLocation[i].latitudeAsString,
                mLocation[i].longitudeAsString,
                mBalloon[i]
            )
            DbModule.RoomDatabase?.offlineDao?.insert(mOfflineLocation)
        }
    }

    private fun getDataFromDatabase() {
        val mLatitude = DbModule.RoomDatabase?.offlineDao?.getAllLat()
        val mLongitude = DbModule.RoomDatabase?.offlineDao?.getAllLong()
        val mDescription = DbModule.RoomDatabase?.offlineDao?.getAllDescription()

        if (mLatitude.isNullOrEmpty()) {
            mView.showNetworkError()
        } else {
            for (i in mLatitude.indices) {
                mLocation.add(
                    LatLng(mLatitude[i].toDouble(), mLongitude!![i].toDouble())
                )
                mBalloon.add(
                    mDescription!![i]
                )
            }
            mView.attachData(mLocation, mBalloon)
        }
    }
}