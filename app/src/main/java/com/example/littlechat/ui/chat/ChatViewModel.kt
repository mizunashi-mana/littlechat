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

    private lateinit var mGroupId: String
    val groupId: String
        get() = mGroupId

    private lateinit var mGroupDisplayName: String
    val groupDisplayName: String
        get() = mGroupDisplayName

    fun setNavArgs(args: ChatFragmentArgs) {
        mGroupId = args.groupId
        mGroupDisplayName = groupId
    }
}
