package id.novian.teravin_challenge.domain.repository

import id.novian.teravin_challenge.domain.model.Movie
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

interface MovieRepository {
    fun getAllPopularMovies(): Observable<List<Movie>>
    fun updateDataOnLocal(movie: List<Movie>): Completable
}