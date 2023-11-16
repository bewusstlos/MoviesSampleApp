package com.bewusstlos.moviessampleapp.ui.viewmodel

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.kodein.di.*
class ViewModelFactory(private val injector: DirectDI) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return injector.instanceOrNull<ViewModel>(tag = modelClass.simpleName) as T?
            ?: modelClass.newInstance()
    }
}

inline fun <reified VM : ViewModel> DI.Builder.bindViewModel(overrides: Boolean? = null): DI.Builder.TypeBinder<VM> {
    return bind<VM>(VM::class.java.simpleName, overrides)
}

inline fun <reified VM : ViewModel, T> T.viewModel(): Lazy<VM> where T : DIAware, T : AppCompatActivity {
    return lazy { ViewModelProvider(this, direct.instance()).get(VM::class.java) }
}