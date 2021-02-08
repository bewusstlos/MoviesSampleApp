package com.bewusstlos.moviessampleapp

import android.app.Application
import com.bewusstlos.moviessampleapp.di.networkModule
import com.bewusstlos.moviessampleapp.di.repositoryModule
import com.bewusstlos.moviessampleapp.di.viewModelModule
import org.kodein.di.*

class MoviesSampleApp : Application(), DIAware {
    override val di: DI by DI.lazy {
        bind<DirectDI>() with singleton { di.direct }
        constant("moviePosterPrefix") with "https://image.tmdb.org/t/p/w500"
        import(networkModule)
        import(repositoryModule)
        import(viewModelModule)
    }
}