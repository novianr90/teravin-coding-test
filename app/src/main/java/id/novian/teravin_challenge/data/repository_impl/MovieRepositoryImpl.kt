package id.novian.teravin_challenge.data.repository_impl

import id.novian.teravin_challenge.data.dataSource.local.MovieDao
import id.novian.teravin_challenge.data.mapper.toDomain
import id.novian.teravin_challenge.data.mapper.toEntity
import id.novian.teravin_challenge.domain.model.Movie
import id.novian.teravin_challenge.domain.repository.MovieRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

class MovieRepositoryImpl(
    private val dataSourceLocal: MovieDao
): MovieRepository {
    override suspend fun getAllPopularMovies(): List<Movie> {
        return dataSourceLocal.getAllPopularMovies().map { it.toDomain() }
    }

    override suspend fun updateDataOnLocal(movie: List<Movie>) {
        dataSourceLocal.deleteAllMovies()
        movie.map { it.toEntity() }
            .forEach { dataSourceLocal.insertDataMovie(it) }
    }

}