package com.bewusstlos.moviessampleapp.data.model

data class PagedMoviesResponse(
    val page: Int,
    val results: List<Movie>
)