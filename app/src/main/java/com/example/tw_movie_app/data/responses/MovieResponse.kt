package com.example.tw_movie_app.data.responses

import com.example.tw_movie_app.data.models.Genre
import com.google.gson.annotations.SerializedName

data class MovieResponse(
    val id: Int = 0,
    val adult: Boolean?,
    @SerializedName("genre_ids")
    val genreIds: List<Int>?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("original_title")
    val originalTitle: String?,
    val overview: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("vote_average")
    val voteAverage: Double?,
    val genres: List<Genre>?
)
