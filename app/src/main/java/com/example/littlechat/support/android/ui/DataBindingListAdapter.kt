package com.example.littlechat.support.android.ui

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class DataBindingListAdapter<T, V : ViewDataBinding>(
    diffCallback: DiffUtil.ItemCallback<T>
): ListAdapter<T, DataBindingViewHolder<V>>(
    AsyncDifferConfig.Builder<T>(diffCallback)
        .build()
) {
    protected abstract fun createBinding(parent: ViewGroup): V
    protected abstract fun bindItem(binding: V, item: T)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder<V> {
        val binding = createBinding(parent)
        return DataBindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder<V>, position: Int) {
        bindItem(holder.binding, getItem(position))
        holder.binding.executePendingBindings()
    }
}

data class DataBindingViewHolder<V : ViewDataBinding>(
    val binding: V
): RecyclerView.ViewHolder(binding.root)
