package com.example.littlechat.module

import dagger.Module

@Module(
    includes = [
        DbModule::class
    ]
)
class AppModule {
}
