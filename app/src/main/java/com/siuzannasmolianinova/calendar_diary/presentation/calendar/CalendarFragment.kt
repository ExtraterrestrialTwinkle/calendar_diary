package com.siuzannasmolianinova.calendar_diary.presentation.calendar

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.siuzannasmolianinova.calendar_diary.R
import com.siuzannasmolianinova.calendar_diary.data.db.entities.Event
import com.siuzannasmolianinova.calendar_diary.databinding.CalendarFragmentBinding
import com.siuzannasmolianinova.calendar_diary.presentation.core.BaseFragment
import com.siuzannasmolianinova.calendar_diary.presentation.core.utils.LocalDateTimeConverter
import com.siuzannasmolianinova.calendar_diary.presentation.core.utils.TimeConverter
import com.siuzannasmolianinova.calendar_diary.presentation.core.utils.showSnackbar
import dagger.hilt.android.AndroidEntryPoint
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import timber.log.Timber
import kotlin.properties.Delegates

@AndroidEntryPoint
class CalendarFragment : BaseFragment<CalendarFragmentBinding>(CalendarFragmentBinding::inflate) {

    private val viewModel: CalendarViewModel by viewModels()
    private lateinit var eventListAdapter: ListAdapter
    private var timeList = arrayOf<String>()
    private var currentDay: Long by Delegates.notNull()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        timeList = resources.getStringArray(R.array.times_list)
        initToolbar()
        initList()
        bindViewModel()

        binding.calendar.setOnDateChangeListener { _, year, month, dayOfMonth ->
            Timber.d("Date is changed!")
            currentDay = LocalDateTime.of(year, month + 1, dayOfMonth, 0, 0)
                .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
            val currentDayString = LocalDateTimeConverter().day(LocalDateTimeConverter().dayTime(currentDay))
            viewModel.loadOneDayList(currentDayString)
        }
    }

    private fun initToolbar() {
        val toolbar = binding.appbar.toolbar
        toolbar.title = getString(R.string.event_list)
    }

    private fun initList() {
        eventListAdapter = ListAdapter {
            findNavController().navigate(
                CalendarFragmentDirections.actionCalendarFragmentToAddFragment(it)
            )
        }.apply {
            timeArray(timeList)
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

    private fun bindViewModel() {
        viewModel.todayEventsList.observe(viewLifecycleOwner) {
            Timber.d("EventList $it")
            eventListAdapter.loadList(it)
        }
        viewModel.error.observe(viewLifecycleOwner) {
            Timber.d("error: ${it.message}")
            showSnackbar(binding.root, null, it.message.toString())
        }
        viewModel.empty.observe(viewLifecycleOwner) { date ->
            Timber.d("emptyList")
            viewModel.saveOneDayEmpty(loadEmptyEventsList(currentDay), date)
        }
    }

    private fun loadEmptyEventsList(day: Long): ArrayList<Event> {
        return arrayListOf<Event>().apply {
            for (i in timeList.indices) {
                try {
                    val timeRange = TimeConverter().convertTimeStringToMillis(timeList, timeList[i])
                    val dateStart = LocalDateTime.ofInstant(
                        Instant.ofEpochMilli(day + timeRange.first),
                        ZoneId.systemDefault()
                    )
                    val dateFinish = LocalDateTime.ofInstant(
                        Instant.ofEpochMilli(day + timeRange.second),
                        ZoneId.systemDefault()
                    )
                    this.add(
                        Event(
//                            id = 0,
                            day = LocalDateTimeConverter().day(LocalDateTimeConverter().dayTime(day)),
                            dateStart = dateStart,
                            dateFinish = dateFinish,
                            description = null,
                            title = null
                        )
                    )
                } catch(e: Exception){
                    showSnackbar(binding.root, null, e.message.toString())
                }
            }
        }
    }
}
