package project.mapbox.cupang.data.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import project.mapbox.cupang.data.model.OfflineLocation

@Database(entities = [OfflineLocation::class], version = 1)
abstract class RoomDbEngine : RoomDatabase() {
    abstract val offlineDao: LocationDAO
}