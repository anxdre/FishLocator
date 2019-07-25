package project.mapbox.cupang.data.Database

import android.app.Application
import androidx.room.Room

class DbModule : Application() {
    companion object {
        var RoomDatabase: RoomDbEngine? = null
    }

    override fun onCreate() {
        super.onCreate()
        RoomDatabase = Room.databaseBuilder(
            applicationContext, RoomDbEngine::class.java,
            "OfflineLocation"
        ).allowMainThreadQueries().build()
    }
}