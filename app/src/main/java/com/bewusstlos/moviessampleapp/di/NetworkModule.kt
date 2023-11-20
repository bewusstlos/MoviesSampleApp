package com.bewusstlos.moviessampleapp.di

import com.bewusstlos.moviessampleapp.data.network.ApiHelper
import com.bewusstlos.moviessampleapp.data.network.ApiService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.kodein.di.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://api.themoviedb.org/"
private const val API_KEY = "ab69f481390fba08b4f04d92ff9da573"

val networkModule = DI.Module("network") {
    bind<OkHttpClient>("httpClient") with singleton {
        val apiKeyInterceptor = Interceptor {chain->
            val url = chain.request()
                .url
                .newBuilder()
                .addQueryParameter("api_key", API_KEY)
                .build()
            val request = chain.request()
                .newBuilder()
                .url(url)
                .build()
            return@Interceptor chain.proceed(request)
        }
        return@singleton OkHttpClient()
            .newBuilder()
            .connectTimeout(1000L, TimeUnit.MILLISECONDS)
            .readTimeout(5000L, TimeUnit.MILLISECONDS)
            .addInterceptor(apiKeyInterceptor)
            .build()
    }

    bind<ApiHelper>() with singleton {
        val apiService = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(instance("httpClient"))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiService::class.java)
        return@singleton ApiHelper(apiService)
    }
}