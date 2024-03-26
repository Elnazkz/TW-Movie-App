package com.example.tw_movie_app.services.network


import com.example.tw_movie_app.data.models.Movie
import com.example.tw_movie_app.services.room.AppDataBase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class MainRepository @Inject constructor(
    private val apiService: ApiService,
    private val db: AppDataBase

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

    suspend fun doSearch(text: String) =
        apiService.search(text)

    suspend fun getMovieDetail(movieId: Int) =
        apiService.getMovieDetails(movieId)

    suspend fun addMovieToFaveDB(movie: Movie): Long =
        db.movieDao.insert(movie)

    suspend fun getMovieByID(id: Int) =
        db.movieDao.getMovieById(id)

    fun getAllMoviesFromFavs() =
        db.movieDao.getAllMoviesFlow()

    suspend fun deleteMovieFromDB(movie: Movie) =
        db.movieDao.delete(movie)
}