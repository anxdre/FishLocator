package project.mapbox.cupang.data.model

import com.google.gson.annotations.SerializedName


data class FishLocation(
    @SerializedName("id")
    val id: String?,
    @SerializedName("marker")
    val marker: String?,
    @SerializedName("latitude")
    val latitude: Double?,
    @SerializedName("longtitude")
    val longtitude: Double?,
    @SerializedName("deskripsi")
    val deskripsi: String?,
    @SerializedName("tgl_update")
    val tglUpdate: String?
)