package com.example.littlechat

import com.example.littlechat.module.AppModule
import com.example.littlechat.module.ContributesInjectorModule
import com.example.littlechat.module.ViewModelModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Singleton

class MainApplication: DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<MainApplication> {
        return DaggerMainApplicationComponent.factory().create(this)
    }
}

@Suppress("unused")
@Singleton
@Component(
    modules = [
        ContributesInjectorModule::class,
        ViewModelModule::class,
        AppModule::class
    ]
)
interface MainApplicationComponent: AndroidInjector<MainApplication> {
    @Component.Factory
    interface Factory: AndroidInjector.Factory<MainApplication>
}
