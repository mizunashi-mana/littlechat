package com.example.littlechat.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.littlechat.R
import com.example.littlechat.databinding.RegisterFragmentBinding
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class RegisterFragment : DaggerFragment() {
    @Module
    abstract class InjectorModule {
        @Suppress("unused")
        @ContributesAndroidInjector
        abstract fun contributeAndroidInjector(): RegisterFragment
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: RegisterViewModel by viewModels {
        viewModelFactory
    }

    private lateinit var binding: RegisterFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.register_fragment,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        binding.linkLogin.setOnClickListener {
            findNavController()
                .navigate(RegisterFragmentDirections.actionLinkLogin())
        }

        binding.buttonRegister.setOnClickListener {
            findNavController()
                .navigate(RegisterFragmentDirections.actionRegister())
        }
    }

}
