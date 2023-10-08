package id.novian.teravin_challenge.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MoviesEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val title: String,
    val originalTitle: String,
    val backdropPath: String,
    val overview: String,
    val posterPath: String,
    val releaseDate: String
)

