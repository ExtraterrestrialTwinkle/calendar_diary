package com.siuzannasmolianinova.calendar_diary.presentation.calendar

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.siuzannasmolianinova.calendar_diary.R
import com.siuzannasmolianinova.calendar_diary.databinding.CalendarFragmentBinding
import com.siuzannasmolianinova.calendar_diary.presentation.core.BaseFragment
import dagger.hilt.EntryPoint
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId

class CalendarFragment : BaseFragment<CalendarFragmentBinding>(CalendarFragmentBinding::inflate) {

    private val viewModel: CalendarViewModel by viewModels()
    private lateinit var eventListAdapter: ListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initList()

        binding.calendar.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val currentDay = LocalDateTime.of(year, month + 1, dayOfMonth, 0, 0)
                .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
            viewModel.loadOneDayList(currentDay)
        }

        binding.fab.setOnClickListener {
            findNavController().navigate(
                CalendarFragmentDirections.actionCalendarFragmentToAddFragment(-1L)
            )
        }
    }

    private fun initToolbar() {
        val toolbar = binding.toolbar.toolbar
        toolbar.title = getString(R.string.event_list)
    }

    private fun initList() {
        eventListAdapter = ListAdapter {
            findNavController().navigate(
                CalendarFragmentDirections.actionCalendarFragmentToAddFragment(it)
            )
        }
        with(binding.eventList) {
            adapter = eventListAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
            setHasFixedSize(true)
        }
    }

    private fun deleteEvent(id: Long) {
        viewModel.deleteEvent(id)
    }
}
