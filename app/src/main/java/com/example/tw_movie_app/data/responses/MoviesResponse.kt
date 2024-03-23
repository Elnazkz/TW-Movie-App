package com.example.tw_movie_app.data.responses

data class MoviesResponse(
    val page: Int,
    val results: List<MovieResponse>
)
