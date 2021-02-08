package com.bewusstlos.moviessampleapp.data.network

import com.bewusstlos.moviessampleapp.data.model.MovieDetails
import com.bewusstlos.moviessampleapp.data.model.PagedMoviesResponse
import com.bewusstlos.moviessampleapp.data.model.PagedSearchMoviesResponse

class ApiHelper(private val apiService: ApiService) {
    suspend fun getPopularMovies(page: Int) : PagedMoviesResponse {
        return apiService.getPopularMovies(page, false)
    }
    suspend fun getSearchMovies(page: Int, query: String) : PagedSearchMoviesResponse {
        return apiService.searchMovies(page, query, false)
    }
    suspend fun getMovieDetails(movieId: Int)  = apiService.getMovieDetails(movieId)
}
