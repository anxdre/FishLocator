package project.mapbox.cupang.data.Api

import kotlinx.coroutines.Deferred

interface ApiRepository {
    fun getDataAsync(): Deferred<Any>
}