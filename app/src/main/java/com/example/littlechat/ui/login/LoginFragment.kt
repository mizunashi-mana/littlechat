package com.example.littlechat.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.littlechat.R
import com.example.littlechat.databinding.LoginFragmentBinding
import com.example.littlechat.support.android.widget.afterTextChanged
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class LoginFragment : DaggerFragment() {
    @Module
    abstract class InjectorModule {
        @Suppress("unused")
        @ContributesAndroidInjector
        abstract fun contributeAndroidInjector(): LoginFragment
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Suppress("unused")
    private val viewModel: LoginViewModel by viewModels {
        viewModelFactory
    }

    private lateinit var binding: LoginFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.login_fragment,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        binding.inputUserId.afterTextChanged {
            afterInputChanged()
        }

        binding.inputPassword.afterTextChanged {
            afterInputChanged()
        }
    }

    private fun afterInputChanged() {
        viewModel.loginDataChanged(
            binding.inputUserId.text.toString(),
            binding.inputPassword.text.toString()
        )
    }
}
