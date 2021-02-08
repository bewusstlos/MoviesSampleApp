package com.bewusstlos.moviessampleapp.data.network

import com.bewusstlos.moviessampleapp.data.model.MovieDetails
import com.bewusstlos.moviessampleapp.data.model.PagedMoviesResponse
import com.bewusstlos.moviessampleapp.data.model.PagedSearchMoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/3/movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int,
                                 @Query("include_adult") includeAdult: Boolean
    ) : PagedMoviesResponse

    @GET("/3/search/movie")
    suspend fun searchMovies(@Query("page") page: Int,
                             @Query("query") query: String,
                             @Query("include_adult") includeAdult: Boolean
    ) : PagedSearchMoviesResponse

    @GET("/3/movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") movieId: Int) : MovieDetails
}