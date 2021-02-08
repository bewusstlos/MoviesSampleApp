package com.bewusstlos.moviessampleapp.ui.viewmodel

import androidx.lifecycle.*
import com.bewusstlos.moviessampleapp.data.model.MovieDetails
import com.bewusstlos.moviessampleapp.data.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieDetailsViewModel(private val movieRepository: MovieRepository) : BaseViewModel() {
    var movieLiveData = MutableLiveData<MovieDetails>()

    fun getMovieDetails(movieId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                withContext(Dispatchers.IO) {
                    movieRepository.getMovieDetails(movieId)
                }
            }.onSuccess {
                movieLiveData.postValue(it)
            }.onFailure {
                handleError(it)
            }
        }
    }
}