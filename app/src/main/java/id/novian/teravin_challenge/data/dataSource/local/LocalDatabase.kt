package id.novian.teravin_challenge.data.dataSource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import id.novian.teravin_challenge.data.model.local.MoviesEntity

@Database(entities = [MoviesEntity::class], version = 1)
abstract class LocalDatabase: RoomDatabase() {
    abstract val dao: MovieDao
}