package com.example.tw_movie_app.services.room

import androidx.room.Dao
import androidx.room.Query
import com.example.tw_movie_app.data.models.Movie
import com.example.tw_movie_app.data.models.TABLE_NAME
import kotlinx.coroutines.flow.Flow

@Dao
abstract class MovieDao : BaseDao<Movie>() {

    @Query("SELECT * FROM $TABLE_NAME WHERE id= :id")
    abstract suspend fun getMovieById(id: Int): Movie?

    @Query("SELECT * FROM $TABLE_NAME")
    abstract fun getAllMoviesFlow(): Flow<List<Movie>>

}