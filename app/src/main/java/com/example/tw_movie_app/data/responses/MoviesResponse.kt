package com.example.tw_movie_app.data.responses

import com.example.tw_movie_app.data.models.Movie

data class MoviesResponse(
    val page: Int,
    val results: List<Movie>
)
