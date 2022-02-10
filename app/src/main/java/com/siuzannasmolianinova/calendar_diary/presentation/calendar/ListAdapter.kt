package com.siuzannasmolianinova.calendar_diary.presentation.calendar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.siuzannasmolianinova.calendar_diary.data.db.entities.Event
import com.siuzannasmolianinova.calendar_diary.databinding.ItemPointBinding
import org.threeten.bp.ZoneId
import timber.log.Timber

class ListAdapter(
    private val onItemClick: (Long) -> Unit
) : ListAdapter<Event, RecyclerView.ViewHolder>(DiffCallback()) {

    private var timeArray = arrayOf<String>()

    fun loadList(list: List<Event>) {
        submitList(list)
    }

    fun timeArray(array: Array<String>) {
        timeArray = array
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

    fun getItemStartTime(position: Int): Long =
        currentList[position].dateStart.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

    inner class PointViewHolder(
        private val binding: ItemPointBinding,
        onItemClick: (Long) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onItemClick(getItemStartTime(bindingAdapterPosition))
            }
        }

        fun bind(event: Event) {
            Timber.d("bind")
            binding.run {
                titleTextView.text = event.title
                if (absoluteAdapterPosition < timeArray.size) {
                    timeTextView.text = timeArray[absoluteAdapterPosition]
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Event>() {
        override fun areItemsTheSame(
            oldItem: Event,
            newItem: Event
        ): Boolean {
            return newItem.title == oldItem.title
        }

        override fun areContentsTheSame(
            oldItem: Event,
            newItem: Event
        ): Boolean {
            return newItem.dateStart == oldItem.dateStart && newItem.description == oldItem.description
                    && newItem.dateFinish == oldItem.dateFinish
        }
    }
}
