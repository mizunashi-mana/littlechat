package com.example.littlechat.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.littlechat.ui.chat.ChatViewModel
import com.example.littlechat.ui.home.HomeViewModel
import com.example.littlechat.ui.login.LoginViewModel
import com.example.littlechat.ui.profile.ProfileViewModel
import com.example.littlechat.ui.register.RegisterViewModel
import dagger.MapKey
import dagger.Module
import dagger.Provides
import javax.inject.Provider
import javax.inject.Singleton
import kotlin.reflect.KClass

@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Module(
    includes = [
        LoginViewModel.ProviderModule::class,
        ChatViewModel.ProviderModule::class,
        HomeViewModel.ProviderModule::class,
        RegisterViewModel.ProviderModule::class,
        ProfileViewModel.ProviderModule::class
    ]
)
class ViewModelModule {
    @Suppress("unused")
    @Singleton
    @Provides
    fun provideViewModelFactory(
        providers: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
    ): ViewModelProvider.Factory {
        return ViewModelFactory(providers)
    }
}

internal class ViewModelFactory(
    private val providers: Map<Class<out ViewModel>, Provider<out ViewModel>>
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val provider = providers[modelClass]
            ?: providers.entries.firstOrNull { modelClass.isAssignableFrom(it.key) }?.value
            ?: throw IllegalArgumentException("Unknown ViewModel class: $modelClass")

        @Suppress("UNCHECKED_CAST")
        return provider.get() as T
    }
}

