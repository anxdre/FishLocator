package project.mapbox.cupang.data.Api

import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import project.mapbox.cupang.data.model.FishLocation

@Suppress("UNCHECKED_CAST")
class ApiFactory : ApiRepository {
    override fun getDataAsync(): Deferred<Any> = reqDataAsync(
        Endpoint.BASE_URL + Endpoint.GET_IKAN , FishLocation::class.java
    )

    private fun reqDataAsync(url: String, type: Class<*>): Deferred<Any> {
        return GlobalScope.async {
            val response = AndroidNetworking
                .get(url)
                .setTag("fetchData")
                .setPriority(Priority.MEDIUM)
                .build()
                .executeForJSONArray()
            if (!response.isSuccess) {
                throw response.error
            }
            response.result

        }
    }
}