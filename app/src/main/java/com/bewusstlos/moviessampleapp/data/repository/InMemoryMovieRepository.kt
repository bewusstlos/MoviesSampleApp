package com.bewusstlos.moviessampleapp.data.repository

import com.bewusstlos.moviessampleapp.data.model.MovieDetails
import com.bewusstlos.moviessampleapp.data.model.PagedMoviesResponse
import com.bewusstlos.moviessampleapp.data.model.PagedSearchMoviesResponse
import com.bewusstlos.moviessampleapp.data.network.ApiHelper

class InMemoryMovieRepository(private val apiHelper: ApiHelper) : MovieRepository {
    override suspend fun getPopularMoviesList(page: Int): PagedMoviesResponse {
        return apiHelper.getPopularMovies(page)
    }

    override suspend fun getQueryResultMoviesList(page: Int, query: String): PagedSearchMoviesResponse {
        return apiHelper.getSearchMovies(page, query)
    }

    override suspend fun getMovieDetails(movieId: Int): MovieDetails {
        return apiHelper.getMovieDetails(movieId)
    }
}