package com.example.littlechat.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.example.littlechat.R
import com.example.littlechat.databinding.RoomItemViewBinding
import com.example.littlechat.model.Room
import com.example.littlechat.support.android.ui.DataBindingListAdapter

class RoomListAdapter(
    private val block: (Room) -> Unit
): DataBindingListAdapter<Room, RoomItemViewBinding>(
    diffCallback = RoomListItemCallback()
) {
    override fun bindItem(binding: RoomItemViewBinding, item: Room) {
        binding.item = item
    }

    override fun createBinding(parent: ViewGroup): RoomItemViewBinding {
        val binding = DataBindingUtil.inflate<RoomItemViewBinding>(
            LayoutInflater.from(parent.context),
            R.layout.room_item_view,
            parent,
            false
        )

        binding.root.setOnClickListener {
            binding.item?.let {
                block(it)
            }
        }

        return binding
    }
}

class RoomListItemCallback: DiffUtil.ItemCallback<Room>() {
    override fun areItemsTheSame(oldItem: Room, newItem: Room): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Room, newItem: Room): Boolean {
        return oldItem.displayName == newItem.displayName
    }
}
