package project.mapbox.cupang.view.mainmenu

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.tomtom.online.sdk.common.location.LatLng
import com.tomtom.online.sdk.map.SimpleMarkerBalloon
import com.tomtom.online.sdk.map.TomtomMap
import project.mapbox.cupang.data.Api.ApiRepository
import project.mapbox.cupang.data.model.FishLocation


class MainMenuPresenter(private val mView: MainMenuView, private val mApi: ApiRepository) {
    private val mGson: Gson = GsonBuilder().create()
    private var mFishLocation = ArrayList<FishLocation>() // retrive from API

    private val mLocation = ArrayList<LatLng>()
    private var mBalloon = ArrayList<String>()


    suspend fun initData(tomtomMap: TomtomMap) {
        mFishLocation.clear()
        mLocation.clear()
        mBalloon.clear()
        val fishLocationJSON = mApi.getDataAsync().await().toString()
        val mFishLocation = mGson.fromJson(fishLocationJSON, Array<FishLocation>::class.java).toList()

        for (i in mFishLocation.indices) {
            mLocation.add(
                LatLng(mFishLocation[i].latitude!!.toDouble(), mFishLocation[i].longtitude!!.toDouble())
            )
            mBalloon.add(
                mFishLocation[i].deskripsi.toString()
            )
        }
        mView.attachData(mLocation,mBalloon)
    }
}