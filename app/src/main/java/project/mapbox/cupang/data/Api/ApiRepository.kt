package project.mapbox.cupang.data.Api

import kotlinx.coroutines.Deferred
import project.mapbox.cupang.data.model.FishLocation

interface ApiRepository {
    fun getDataAsync(): Deferred<MutableList<FishLocation>>
}