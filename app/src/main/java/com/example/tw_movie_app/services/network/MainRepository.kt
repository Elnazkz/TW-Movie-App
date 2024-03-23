package com.example.tw_movie_app.services.network


import javax.inject.Inject


class MainRepository @Inject constructor(
    private val apiService: ApiService
) {
//    companion object{
//        const val BASIC_AUTH =
//            "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3Y2IxNDE0ZWJlZmExMzFlNDVmNDVkZGM1YWY5OTExMCIsInN1YiI6IjY1ZmRiNGU0NjA2MjBhMDE0OTI2NTQyYiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.XJ0_IkwytX8eYSBNqka7Lv1i5uBlAsk8RazE3N7bgA0"
//    }
    suspend fun getPopularMovies() =
        apiService.getPopularMovies(
//            baseAuth = BASIC_AUTH
        )

    suspend fun getGenres() =
        apiService.getGenres()

}