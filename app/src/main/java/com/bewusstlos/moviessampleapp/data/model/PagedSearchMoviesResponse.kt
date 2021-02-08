package com.bewusstlos.moviessampleapp.data.model

import com.google.gson.annotations.SerializedName

data class PagedSearchMoviesResponse(
    val page: Int,
    val results: List<Movie>,
    @SerializedName("total_results")
    val totalResults: Int,
    @SerializedName("total_pages")
    val totalPages: Int
)