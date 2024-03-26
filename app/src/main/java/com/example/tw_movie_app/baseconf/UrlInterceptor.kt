package com.example.tw_movie_app.baseconf

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class UrlInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url

        val newUrlBuilder = url.newBuilder()
            .addQueryParameter(API_KEY, API_KEY_VALUE)

        val newRequestBuilder = request.newBuilder()
            .url(newUrlBuilder.build())

        return chain.proceed(newRequestBuilder.build())
    }

}