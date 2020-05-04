package com.example.littlechat.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.littlechat.R
import com.example.littlechat.support.android.dagger.DaggerPreferenceFragmentCompat
import dagger.Module
import dagger.android.ContributesAndroidInjector
import java.lang.RuntimeException

class SettingsFragment : DaggerPreferenceFragmentCompat() {
    @Module
    abstract class InjectorModule {
        @Suppress("unused")
        @ContributesAndroidInjector
        abstract fun contributeAndroidInjector(): SettingsFragment
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState) ?: return null

        val targetLayout = view.findViewById<View>(android.R.id.list_container)
            ?.parent
        if (targetLayout !is LinearLayout) {
            println(targetLayout.toString())
            throw RuntimeException("Expect preference linear layout")
        }

        val toolbar = layoutInflater.inflate(R.layout.settings_toolbar, null) as Toolbar
        targetLayout.addView(toolbar, 0)
        toolbar.setupWithNavController(findNavController())

        return view
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }
}
