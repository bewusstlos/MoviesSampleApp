package com.bewusstlos.moviessampleapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class BaseViewModel : ViewModel() {
    val errorLiveData = MutableLiveData<String>()

    fun handleError(error: Throwable) {
        when {
            (error is UnknownHostException || error is SocketTimeoutException)-> errorLiveData.postValue("Unable to connect...")
            else -> errorLiveData.postValue(error.localizedMessage)
        }
    }
}