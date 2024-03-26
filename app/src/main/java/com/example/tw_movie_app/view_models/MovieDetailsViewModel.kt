package com.example.tw_movie_app.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tw_movie_app.data.models.Movie
import com.example.tw_movie_app.data.responses.MovieResponse
import com.example.tw_movie_app.services.network.MainRepository
import com.example.tw_movie_app.services.network.ServiceType
import com.example.tw_movie_app.services.network.Status
import com.example.tw_movie_app.services.network.getData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.ceil


@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _getMovieDetails = MutableStateFlow<Movie?>(null)
    val getMovieDetails: StateFlow<Movie?> = _getMovieDetails

    private val _error = Channel<String>()
    val error = _error.receiveAsFlow()

    private fun sendError(error: String) {
        _error.trySend(
            error
        )
    }

    fun getMovieDetail(id: Int) {
        viewModelScope.launch {
            val response = getData<Int, MovieResponse>(
                serviceType = ServiceType.MOVIE_DETAILS,
                mainRepository = mainRepository,
                input = id
            )

            if (response.status == Status.SUCCESS && response.data != null) {
                _getMovieDetails.value = setMovie(response.data)
            } else {
                response.message?.let { sendError(it) }
            }
        }
    }

    private fun setMovie(movie: MovieResponse): Movie {
        return Movie(
            id = movie.id,
            genres = movie.genres?.map { it.name } ?: listOf(),
            backdropPath = movie.backdropPath ?: "",
            posterPath = movie.posterPath ?: "",
            originalTitle = movie.originalTitle ?: "Title",
            overview = movie.overview ?: "",
            releaseDate = if (movie.releaseDate == null || movie.releaseDate == "") "1900" else movie.releaseDate.substring(0, 4),
            votePercentage = ceil((movie.voteAverage?.div(10))?.times(100) ?: 0.0).toInt()
        )
    }

}