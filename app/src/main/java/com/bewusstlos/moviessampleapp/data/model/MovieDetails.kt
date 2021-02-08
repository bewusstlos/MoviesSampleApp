package com.bewusstlos.moviessampleapp.data.model

import com.google.gson.annotations.SerializedName

data class MovieDetails(
    val id: Int,
    @SerializedName("imdb_id")
    val imdbId: String?,
    @SerializedName("adult")
    val isAdult: Boolean,
    val budget: Int,
    val genres: List<Genre>,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_title")
    val originalTitle: String,
    val overview: String?,
    val popularity: Float,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompany>,
    @SerializedName("production_countries")
    val productionCountries: List<ProductionCountry>,
    @SerializedName("release_date")
    val releaseDate: String,
    val revenue: Int,
    val runtime: Int?,
    @SerializedName("spoken_languages")
    val spokenLanguages: List<Language>,
    val status: String,
    val tagLine: String?,
    val title: String,
    val video: Boolean,
    @SerializedName("vote_average")
    val voteAverage: Float,
    @SerializedName("vote_count")
    val voteCount: Int,
    @SerializedName("home_page")
    val homePage: String?
) {
    data class Genre(
        val id: Int,
        val name: String
    )

    data class ProductionCompany(
        val id: Int,
        val name: String,
        @SerializedName("logo_path")
        val logoPath: String?,
        @SerializedName("origin_country")
        val originCountry: String
    )

    data class ProductionCountry(
        @SerializedName("iso_3166_1")
        val isoIndex: String,
        val name: String
    )

    data class Language(
        @SerializedName("iso_639_1")
        val isoIndex: String,
        val name: String
    )
}

