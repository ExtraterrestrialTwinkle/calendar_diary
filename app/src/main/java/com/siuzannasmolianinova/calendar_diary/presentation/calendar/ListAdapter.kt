package com.siuzannasmolianinova.calendar_diary.presentation.calendar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.siuzannasmolianinova.calendar_diary.data.EventVisible
import com.siuzannasmolianinova.calendar_diary.databinding.ItemPointBinding

class ListAdapter(
    private val onItemClick: (Long) -> Unit
) : ListAdapter<EventVisible, RecyclerView.ViewHolder>(DiffCallback()) {
    fun loadList(list: List<EventVisible>) {
        submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemPointBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PointViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PointViewHolder).run {
            bind(currentList[position])
        }
    }

    override fun getItemId(position: Int): Long = currentList[position].id

    fun getItemStartTime(position: Int): Long = currentList[position].dateStart.toEpochMilli()

    inner class PointViewHolder(
        private val binding: ItemPointBinding,
        onItemClick: (Long) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.deleteButton.setOnClickListener {
                onItemClick(getItemStartTime(bindingAdapterPosition))
            }
        }

        fun bind(event: EventVisible) {
            binding.run {
                titleTextView.text = event.title
                timeTextView.text = event.dateStart.toString() // поменять
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<EventVisible>() {
        override fun areItemsTheSame(
            oldItem: EventVisible,
            newItem: EventVisible
        ): Boolean {
            return newItem.id == oldItem.id
        }

        override fun areContentsTheSame(
            oldItem: EventVisible,
            newItem: EventVisible
        ): Boolean {
            return newItem == oldItem
        }
    }
}
