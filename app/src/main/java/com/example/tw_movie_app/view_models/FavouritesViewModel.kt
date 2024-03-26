package com.example.tw_movie_app.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tw_movie_app.data.models.Movie
import com.example.tw_movie_app.services.network.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _getAllFavourites = MutableStateFlow<List<Movie>>(listOf())
    val getAllFavourites: StateFlow<List<Movie>> = _getAllFavourites

    private val _insertionResult = MutableStateFlow<Long>(-1)
    val insertionResult: StateFlow<Long> = _insertionResult

    private val _deleteResult = MutableStateFlow<Int>(0)
    val deleteResult: StateFlow<Int> = _deleteResult

    private val _error = Channel<String>()
    val error = _error.receiveAsFlow()

    private fun sendError(error: String) {
        _error.trySend(
            error
        )
    }

   fun getAllFavsCall() {
       viewModelScope.launch {
           mainRepository.getAllMoviesFromFavs().collectLatest {
               _getAllFavourites.value = it
           }
       }
   }

    fun updateMovieFaveDB(movie: Movie) {
        viewModelScope.launch {
            if (movie.favourite)
                _deleteResult.value = mainRepository.deleteMovieFromDB(movie)
            else {
                movie.favourite = true
                _insertionResult.value = mainRepository.addMovieToFaveDB(movie)
            }
        }
    }




}