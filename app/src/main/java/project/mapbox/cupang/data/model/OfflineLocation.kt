package project.mapbox.cupang.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class OfflineLocation(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @ColumnInfo(name = "mLatitude")
    var mLatitude: String,
    @ColumnInfo(name = "mLongitude")
    val mLongitude: String,
    @ColumnInfo(name = "mDeskripsi")
    val mDeskripsi: String
)
