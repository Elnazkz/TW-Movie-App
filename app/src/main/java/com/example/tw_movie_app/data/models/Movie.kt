package com.example.tw_movie_app.data.models


data class Movie(
    val id: Int = 0,
    val genres: List<String>,
    val backdropPath: String,
    val posterPath: String,
    val originalTitle: String,
    val overview: String,
    val releaseDate: String,
    val votePercentage: Int,
    val favourite: Boolean = false
)
