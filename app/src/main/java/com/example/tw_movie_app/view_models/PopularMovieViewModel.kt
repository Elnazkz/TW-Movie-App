package com.example.tw_movie_app.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tw_movie_app.data.models.Movie
import com.example.tw_movie_app.data.responses.BaseResponse
import com.example.tw_movie_app.data.responses.MoviesResponse
import com.example.tw_movie_app.services.network.MainRepository
import com.example.tw_movie_app.services.network.Resource
import com.example.tw_movie_app.services.network.ServiceType
import com.example.tw_movie_app.services.network.Status
import com.example.tw_movie_app.services.network.getDataFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PopularMovieViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _getMovies = MutableStateFlow<Resource<MoviesResponse>>(Resource.initialize())
    val getMovies: StateFlow<Resource<MoviesResponse>> = _getMovies

   fun getMovies() {
       viewModelScope.launch {
           getDataFlow(
               service = ServiceType.GET_POPULAR_MOVIES,
               mainRepository = mainRepository,
               pathInput = null,
               input = null,
               flowData = _getMovies
           )
       }
   }


}