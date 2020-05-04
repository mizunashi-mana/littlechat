package com.example.littlechat.ui.register

import androidx.lifecycle.ViewModel
import com.example.littlechat.module.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Inject

class RegisterViewModel @Inject constructor() : ViewModel() {
    @Module
    abstract class ProviderModule {
        @Suppress("unused")
        @Binds
        @IntoMap
        @ViewModelKey(RegisterViewModel::class)
        abstract fun provideViewModel(viewModel: RegisterViewModel): ViewModel
    }

    val isDataValid: Boolean = false
}
