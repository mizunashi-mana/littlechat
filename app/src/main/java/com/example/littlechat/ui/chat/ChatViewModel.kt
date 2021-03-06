package com.example.littlechat.ui.chat

import androidx.lifecycle.ViewModel
import com.example.littlechat.module.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Inject

class ChatViewModel @Inject constructor() : ViewModel() {
    @Module
    abstract class ProviderModule {
        @Suppress("unused")
        @Binds
        @IntoMap
        @ViewModelKey(ChatViewModel::class)
        abstract fun provideViewModel(viewModel: ChatViewModel): ViewModel
    }

    private lateinit var navArgs: ChatFragmentArgs

    val roomId: String
        get() = navArgs.roomId

    lateinit var roomDisplayName: String

    fun setNavArgs(args: ChatFragmentArgs) {
        navArgs = args
        roomDisplayName = roomId
    }
}
