package com.bewusstlos.moviessampleapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bewusstlos.moviessampleapp.data.model.Movie
import com.bewusstlos.moviessampleapp.data.repository.MovieRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ListViewModel(private val movieRepository: MovieRepository) : BaseViewModel() {
    val moviesLiveData = MutableLiveData<List<Movie>>()
    val isLoadingLiveData = MutableLiveData<Boolean>()
    val searchResultsCount = MutableLiveData<Int>()

    private var pageToLoad = 0
    private var couldLoadSearchResult = true
    private var loadingJob: Job? = null

    fun clearData() {
        pageToLoad = 0
        moviesLiveData.postValue(emptyList())
        searchResultsCount.postValue(-1)
        isLoadingLiveData.postValue(true)
    }

    fun loadMovies(query: String?) {
        pageToLoad++
        if (pageToLoad > 5 && query.isNullOrEmpty())
            return
        isLoadingLiveData.postValue(true)
        if (!query.isNullOrEmpty()) {
            requestSearchMovies(pageToLoad, query)
        } else {
            requestPopularMovies(pageToLoad)
        }
    }

    fun requestPopularMovies(page: Int) {
        loadingJob = viewModelScope.launch {
            kotlin.runCatching {
                movieRepository.getPopularMoviesList(page)
            }.onSuccess {
                moviesLiveData.postValue(it.results)
            }.onFailure {
                handleError(it)
            }
        }
        loadingJob?.invokeOnCompletion { isLoadingLiveData.postValue(false) }
    }

    fun requestSearchMovies(page: Int, query: String) {
        loadingJob = viewModelScope.launch {
            kotlin.runCatching {
                withContext(Dispatchers.IO) {
                    movieRepository.getQueryResultMoviesList(page, query)
                }
            }.onSuccess {
                moviesLiveData.postValue(it.results)
                searchResultsCount.postValue(it.totalResults)
                couldLoadSearchResult = pageToLoad + 1 <= it.totalPages
            }.onFailure {
                handleError(it)
            }
        }
        loadingJob?.invokeOnCompletion { isLoadingLiveData.postValue(false) }
    }
}