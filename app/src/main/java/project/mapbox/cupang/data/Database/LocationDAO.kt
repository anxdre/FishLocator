package project.mapbox.cupang.data.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import project.mapbox.cupang.data.model.OfflineLocation

@Dao
interface LocationDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(offlineLocation: OfflineLocation)

    @Query("DELETE from OfflineLocation")
    fun deleteAll()

    @Query("SELECT mLatitude FROM OfflineLocation ")
    fun getAllLat(): List<String>

    @Query("SELECT mLongitude FROM OfflineLocation ")
    fun getAllLong(): List<String>

    @Query("SELECT mDeskripsi FROM OfflineLocation")
    fun getAllDescription(): List<String>
}