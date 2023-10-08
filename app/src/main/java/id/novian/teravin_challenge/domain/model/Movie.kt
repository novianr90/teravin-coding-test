package id.novian.teravin_challenge.domain.model

data class Movie(
    val id: Int,
    val title: String,
    val originalTitle: String,
    val backdropPath: String,
    val overview: String,
    val posterPath: String,
    val releaseDate: String
)
