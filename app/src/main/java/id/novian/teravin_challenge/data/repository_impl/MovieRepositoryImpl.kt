package id.novian.teravin_challenge.data.repository_impl

import id.novian.teravin_challenge.data.dataSource.local.MovieDao
import id.novian.teravin_challenge.data.mapper.toDomain
import id.novian.teravin_challenge.domain.model.Movie
import id.novian.teravin_challenge.domain.repository.MovieRepository
import io.reactivex.rxjava3.core.Observable

class MovieRepositoryImpl(
    private val dataSourceLocal: MovieDao
): MovieRepository {
    override fun getAllPopularMovies(): Observable<List<Movie>> {
        return dataSourceLocal.getAllPopularMovies()
            .map { movie ->
                val mapped = movie.map {
                    it.toDomain()
                }
                mapped
            }
    }
}