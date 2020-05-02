package com.example.littlechat.ui.home

import androidx.lifecycle.ViewModel
import com.example.littlechat.module.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Inject

class HomeViewModel @Inject constructor() : ViewModel() {
    @Module
    abstract class ProviderModule {
        @Suppress("unused")
        @Binds
        @IntoMap
        @ViewModelKey(HomeViewModel::class)
        abstract fun provideViewModel(viewModel: HomeViewModel): ViewModel
    }
}
