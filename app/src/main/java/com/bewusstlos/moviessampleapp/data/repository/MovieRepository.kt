package com.bewusstlos.moviessampleapp.data.repository

import android.app.DownloadManager
import com.bewusstlos.moviessampleapp.data.model.Movie
import com.bewusstlos.moviessampleapp.data.model.MovieDetails
import com.bewusstlos.moviessampleapp.data.model.PagedMoviesResponse
import com.bewusstlos.moviessampleapp.data.model.PagedSearchMoviesResponse

interface MovieRepository {
    suspend fun getPopularMoviesList(page: Int) : PagedMoviesResponse
    suspend fun getQueryResultMoviesList(page: Int, query: String) : PagedSearchMoviesResponse
    suspend fun getMovieDetails(movieId: Int) : MovieDetails
}