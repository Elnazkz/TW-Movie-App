package com.example.tw_movie_app.services.network

import com.example.tw_movie_app.data.responses.GenresResponse
import com.example.tw_movie_app.data.responses.MovieResponse
import com.example.tw_movie_app.data.responses.MoviesResponse
import com.example.tw_movie_app.data.responses.SearchResponse
import retrofit2.http.*

const val AUTHORIZATION = "Authorization"

interface ApiService {

    @GET(GET_POPULAR_MOVIES)
    suspend fun getPopularMovies(
//        @Header(AUTHORIZATION) baseAuth: String
    ): MoviesResponse

    @GET(GET_GENRES)
    suspend fun getGenres(
    ): GenresResponse

    @GET(SEARCH)
    suspend fun search(
        @Query(QUERY_PATH_KEY) searchString: String
    ): SearchResponse

    @GET(MOVIE_DETAILS)
    suspend fun getMovieDetails(
        @Path(MOVIE_ID_PATH_KEY) movieId: Int
    ): MovieResponse
}