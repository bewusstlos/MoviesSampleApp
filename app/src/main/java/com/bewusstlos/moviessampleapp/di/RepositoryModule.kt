package com.bewusstlos.moviessampleapp.di

import com.bewusstlos.moviessampleapp.data.repository.InMemoryMovieRepository
import com.bewusstlos.moviessampleapp.data.repository.MovieRepository
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val repositoryModule = DI.Module("repository") {
    bind<MovieRepository>() with singleton { InMemoryMovieRepository(instance()) }
}