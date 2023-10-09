package id.novian.teravin_challenge.data.dataSource.network

import id.novian.teravin_challenge.data.model.network.Movies
import id.novian.teravin_challenge.util.Constant
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("movie")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = "f7b67d9afdb3c971d4419fa4cb667fbf"
    ): Call<Movies>

}