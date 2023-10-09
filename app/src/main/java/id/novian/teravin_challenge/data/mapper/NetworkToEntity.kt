package id.novian.teravin_challenge.data.mapper

import id.novian.teravin_challenge.data.model.local.MoviesEntity
import id.novian.teravin_challenge.data.model.network.Result
import id.novian.teravin_challenge.domain.model.Movie

fun Result.toMovieEntity(): MoviesEntity {
    return MoviesEntity(
        id = this.id,
        title = this.title,
        originalTitle = this.originalTitle,
        backdropPath = this.backdropPath,
        overview = this.overview,
        posterPath = this.posterPath,
        releaseDate = this.releaseDate
    )
}

fun Result.toDomain(): Movie {
    return Movie(
        id = this.id,
        title = this.title,
        originalTitle = this.originalTitle,
        backdropPath = this.backdropPath,
        overview = this.overview,
        posterPath = this.posterPath,
        releaseDate = this.releaseDate
    )
}