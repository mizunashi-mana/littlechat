package com.example.littlechat.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.littlechat.MainNavigationDirections
import com.example.littlechat.R
import com.example.littlechat.databinding.HomeFragmentBinding
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class HomeFragment : DaggerFragment() {
    @Module
    abstract class InjectorModule {
        @Suppress("unused")
        @ContributesAndroidInjector
        abstract fun contributeAndroidInjector(): HomeFragment
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: HomeViewModel by viewModels {
        viewModelFactory
    }

    private lateinit var binding: HomeFragmentBinding
    private lateinit var adapter: RoomListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.home_fragment,
            container,
            false
        )

        binding.toolbar.setupWithNavController(findNavController())

        adapter = RoomListAdapter { room ->
            findNavController()
                .navigate(HomeFragmentDirections.actionChat(room.id))
        }
        binding.roomListView.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        viewModel.rooms.observe(
            viewLifecycleOwner,
            Observer { rooms ->
                adapter.submitList(rooms ?: emptyList())
            }
        )

        binding.toolbar.setOnMenuItemClickListener {
            onOptionsItemSelected(it)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.navigation_settings -> {
                findNavController()
                    .navigate(MainNavigationDirections.actionSettings())
                true
            }
            R.id.navigation_profile -> {
                findNavController()
                    .navigate(MainNavigationDirections.actionProfile())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
