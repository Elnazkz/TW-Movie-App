package com.example.tw_movie_app.services.network
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow

data class Resource<out T>(val status: Status, val data: T? = null, val message: String? = null) {
    companion object {
        fun <T> initialize(): Resource<T> =
            Resource(status = Status.NONE)


        fun <T> success(data: T?): Resource<T> =
            Resource(status = Status.SUCCESS, data = data)

        fun <T> error(message: String): Resource<T> =
            Resource(status = Status.ERROR, message = message)

        fun <T> loading(): Resource<T> =
            Resource(status = Status.LOADING)
    }
}


suspend fun <T, G> getDataFlow(
    service: ServiceType,
    mainRepository: MainRepository,
    input: T,
    flowData: MutableStateFlow<Resource<G>>
)  {
    var response: Any?
    CoroutineScope(Dispatchers.IO).launch {
        try {
            response = when(service) {
                ServiceType.GET_POPULAR_MOVIES -> mainRepository.getPopularMovies()
                ServiceType.GET_GENRES -> mainRepository.getGenres()
                ServiceType.SEARCH_MOVIE -> mainRepository.doSearch(input as String)
                ServiceType.MOVIE_DETAILS -> mainRepository.getMovieDetail(input as Int)
            }

            flowData.value = Resource.success( data = response as G)

        } catch (exception: Exception) {
            //TODO check for apis error format
            exception.printStackTrace()

            flowData.value = Resource.error(
                message = "Fetch data error, please try again later!"
            )

        }
    }
}

suspend fun <T,G> getData(
    serviceType: ServiceType,
    mainRepository: MainRepository,
    input: T?,
): Resource<G> {

    try {
        return withContext(Dispatchers.IO) {
            val response = when (serviceType) {
                ServiceType.GET_POPULAR_MOVIES -> mainRepository.getPopularMovies()
                //other services will be added below
                ServiceType.GET_GENRES -> mainRepository.getGenres()
                ServiceType.SEARCH_MOVIE -> mainRepository.doSearch(input as String)
                ServiceType.MOVIE_DETAILS -> mainRepository.getMovieDetail(input as Int)
            }

            Resource.success( data = response as G)
        }

    } catch (exception: Exception) {
        exception.printStackTrace()
        //TODO check for apis error format
        return Resource.error(
            message = "Fetch data error, please try again later!"
        )

    }

}