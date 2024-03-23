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
    pathInput: T,
    input: T,
    flowData: MutableStateFlow<Resource<G>>
) {
    var response: Any?
    CoroutineScope(Dispatchers.IO).launch {
        try {
            response = when(service) {
                ServiceType.GET_POPULAR_MOVIES -> mainRepository.getPopularMovies()
            }

            flowData.value = Resource.success( data = response as G)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
