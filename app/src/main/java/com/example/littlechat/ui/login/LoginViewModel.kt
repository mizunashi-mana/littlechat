package com.example.littlechat.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.littlechat.module.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Inject

class LoginViewModel @Inject constructor() : ViewModel() {
    @Module
    abstract class ProviderModule {
        @Suppress("unused")
        @Binds
        @IntoMap
        @ViewModelKey(LoginViewModel::class)
        abstract fun provideViewModel(viewModel: LoginViewModel): ViewModel
    }

    private val mutableIsDataValid: MutableLiveData<Boolean> = MutableLiveData(false)
    val isDataValid: LiveData<Boolean> = mutableIsDataValid

    private val mutableErrorUserId: MutableLiveData<String?> = MutableLiveData(null)
    val errorUserId: LiveData<String?> = mutableErrorUserId

    private val mutableErrorPassword: MutableLiveData<String?> = MutableLiveData(null)
    val errorPassword: LiveData<String?> = mutableErrorPassword

    fun loginDataChanged(
        user: String,
        password: String
    ) {
        var isValid = true

        if (user.isBlank()) {
            isValid = false
        }

        if (password.isBlank()) {
            isValid = false
        }

        mutableIsDataValid.value = isValid
    }
}
