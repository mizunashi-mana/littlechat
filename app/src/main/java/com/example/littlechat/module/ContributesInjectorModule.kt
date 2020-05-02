package com.example.littlechat.module

import com.example.littlechat.ui.MainActivity
import com.example.littlechat.ui.login.LoginFragment
import dagger.Module
import dagger.android.AndroidInjectionModule

@Module(
    includes = [
        AndroidInjectionModule::class,
        MainActivity.InjectorModule::class,
        LoginFragment.InjectorModule::class
    ]
)
abstract class ContributesInjectorModule
