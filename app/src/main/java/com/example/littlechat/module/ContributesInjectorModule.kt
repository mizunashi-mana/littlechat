package com.example.littlechat.module

import com.example.littlechat.ui.MainActivity
import com.example.littlechat.ui.chat.ChatFragment
import com.example.littlechat.ui.home.HomeFragment
import com.example.littlechat.ui.login.LoginFragment
import com.example.littlechat.ui.register.RegisterFragment
import com.example.littlechat.ui.settings.SettingsFragment
import dagger.Module
import dagger.android.AndroidInjectionModule

@Module(
    includes = [
        AndroidInjectionModule::class,
        MainActivity.InjectorModule::class,
        LoginFragment.InjectorModule::class,
        ChatFragment.InjectorModule::class,
        HomeFragment.InjectorModule::class,
        SettingsFragment.InjectorModule::class,
        RegisterFragment.InjectorModule::class
    ]
)
abstract class ContributesInjectorModule
