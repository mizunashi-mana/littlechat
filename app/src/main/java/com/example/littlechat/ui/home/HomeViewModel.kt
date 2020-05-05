package com.example.littlechat.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.littlechat.model.Room
import com.example.littlechat.module.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import java.util.*
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

    val rooms: LiveData<List<Room>> = MutableLiveData(
        listOf(
            Room(
                "room1",
                "Room 1"
            ),
            Room(
                "room2",
                "Room 2"
            )
        )
    )
}
