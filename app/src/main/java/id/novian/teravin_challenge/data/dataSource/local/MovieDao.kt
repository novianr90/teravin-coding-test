package id.novian.teravin_challenge.data.dataSource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.novian.teravin_challenge.data.model.local.MoviesEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDataMovie(entity: MoviesEntity)

    @Query("SELECT * FROM MoviesEntity")
    suspend fun getAllPopularMovies(): List<MoviesEntity>

    @Query("DELETE FROM MoviesEntity")
    suspend fun deleteAllMovies()
}