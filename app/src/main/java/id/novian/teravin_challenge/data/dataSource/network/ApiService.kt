package id.novian.teravin_challenge.data.dataSource.network

import id.novian.teravin_challenge.data.model.network.Movies
import id.novian.teravin_challenge.util.Constant
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface ApiService {

    @GET
    fun getPopularMovies(): Observable<Movies>

}