package com.bewusstlos.moviessampleapp.di

import com.bewusstlos.moviessampleapp.ui.viewmodel.ListViewModel
import com.bewusstlos.moviessampleapp.ui.viewmodel.MovieDetailsViewModel
import com.bewusstlos.moviessampleapp.ui.viewmodel.ViewModelFactory
import com.bewusstlos.moviessampleapp.ui.viewmodel.bindViewModel
import org.kodein.di.*

val viewModelModule = DI.Module("viewModel") {
    bind<ViewModelFactory>() with provider { ViewModelFactory(instance()) }
    bindViewModel<ListViewModel>() with provider { ListViewModel(instance()) }
    bindViewModel<MovieDetailsViewModel>() with provider { MovieDetailsViewModel(instance())}
}