package com.example.tw_movie_app.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tw_movie_app.data.models.Genre
import com.example.tw_movie_app.data.models.Movie
import com.example.tw_movie_app.data.responses.GenresResponse
import com.example.tw_movie_app.data.responses.MovieResponse
import com.example.tw_movie_app.data.responses.MoviesResponse
import com.example.tw_movie_app.data.responses.SearchResponse
import com.example.tw_movie_app.services.network.MainRepository
import com.example.tw_movie_app.services.network.Resource
import com.example.tw_movie_app.services.network.ServiceType
import com.example.tw_movie_app.services.network.Status
import com.example.tw_movie_app.services.network.getData
import com.example.tw_movie_app.services.network.getDataFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.ArrayList
import javax.inject.Inject
import kotlin.math.ceil


@HiltViewModel
class PopularMovieViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private var genres = listOf<Genre>()
    private var originalMovies = listOf<Movie>()

    private val _getMovies = MutableStateFlow<Resource<List<Movie>>>(Resource.initialize())
    val getMovies: StateFlow<Resource<List<Movie>>> = _getMovies

    private val _getGenres = MutableStateFlow<Resource<GenresResponse>>(Resource.initialize())
    val getGenres: StateFlow<Resource<GenresResponse>> = _getGenres

    init {
        viewModelScope.launch {
            _getGenres.collect {
                if (it.status == Status.SUCCESS && it.data != null) {
                    genres = it.data.genres
                }
            }
        }
        getGenres()
    }

    private fun getGenres() {
        viewModelScope.launch {
            getDataFlow(
                service = ServiceType.GET_GENRES,
                mainRepository = mainRepository,
                input = null,
                flowData = _getGenres
            )
        }

    }

    fun getMovies() {
        viewModelScope.launch {
//           getDataFlow(
//               service = ServiceType.GET_POPULAR_MOVIES,
//               mainRepository = mainRepository,
//               pathInput = null,
//               input = null,
//               flowData = _getMovies
//           )


            val response = getData<Any?, MoviesResponse>(
                serviceType = ServiceType.GET_POPULAR_MOVIES,
                mainRepository = mainRepository,
                input = null
            )

            if (response.status == Status.SUCCESS && response.data != null) {
                originalMovies = setMovieList(response.data.results)
                _getMovies.value = Resource(status = Status.SUCCESS, data = originalMovies)

            } else {
                _getMovies.value = Resource(status = Status.ERROR , message = "Error")
            }
        }
    }

    fun doSearch(input: String) {
        viewModelScope.launch {
            val response = getData<String, SearchResponse>(
                serviceType = ServiceType.SEARCH_MOVIE,
                mainRepository = mainRepository,
                input = input
            )

            if (response.status == Status.SUCCESS && response.data != null) {
                _getMovies.value = Resource(status = Status.SUCCESS, data = setMovieList(response.data.results))

            }else {
                _getMovies.value = Resource(status = Status.ERROR , message = "Error")
            }
        }
    }

    private fun setMovieList(list: List<MovieResponse>): List<Movie> {

        return list.map { movie ->
            Movie(
                id = movie.id,
                genres = (movie.genreIds ?: listOf()).map { id ->
                    genres.find { id == it.id }?.name ?: "Genre"
                },
                backdropPath = movie.backdropPath ?: "",
                posterPath = movie.posterPath ?: "",
                originalTitle = movie.originalTitle ?: "Title",
                overview = movie.overview ?: "",
                releaseDate = if (movie.releaseDate == null || movie.releaseDate == "") "1900" else movie.releaseDate.substring(0, 4),
                votePercentage = ceil((movie.voteAverage?.div(10))?.times(100) ?: 0.0).toInt()
            )

        }

    }

    fun resetToOriginalList() {
        _getMovies.value = Resource(status = Status.SUCCESS, data = originalMovies)
    }

}