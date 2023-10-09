package id.novian.teravin_challenge.data.repository_impl

import android.util.Log
import id.novian.teravin_challenge.data.dataSource.local.MovieDao
import id.novian.teravin_challenge.data.mapper.toDomain
import id.novian.teravin_challenge.data.mapper.toEntity
import id.novian.teravin_challenge.domain.model.Movie
import id.novian.teravin_challenge.domain.repository.MovieRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepositoryImpl(
    private val dataSourceLocal: MovieDao
): MovieRepository {
    override suspend fun getAllPopularMovies(): List<Movie> {
        return withContext(Dispatchers.IO) {
            val data = dataSourceLocal.getAllPopularMovies().map { it.toDomain() }
            Log.i("repo", "get size is ${data.size}")
            data
        }
    }

    override suspend fun updateDataOnLocal(movie: List<Movie>) {
        Log.i("repo", "update data size is ${movie.size}")
        withContext(Dispatchers.IO) {
            dataSourceLocal.deleteAllMovies()
            movie.map { it.toEntity() }
                .forEach { dataSourceLocal.insertDataMovie(it) }
        }
    }

}