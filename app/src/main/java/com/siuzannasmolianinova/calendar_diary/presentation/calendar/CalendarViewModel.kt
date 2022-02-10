package com.siuzannasmolianinova.calendar_diary.presentation.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.siuzannasmolianinova.calendar_diary.data.db.EventDao
import com.siuzannasmolianinova.calendar_diary.data.db.entities.Event
import com.siuzannasmolianinova.calendar_diary.presentation.core.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(private val eventDao: EventDao) : ViewModel() {

    private val _todayEventsList = MutableLiveData<List<Event>>()
    val todayEventsList: LiveData<List<Event>> get() = _todayEventsList

    private val _empty = SingleLiveEvent<String>()
    val empty: LiveData<String> get() = _empty

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable> get() = _error

    fun loadOneDayList(date: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val list = eventDao.openDay(date)
                if (list.isEmpty()) {
                    _empty.postValue(date)
                } else _todayEventsList.postValue(list)
            } catch (t: Throwable) {
                Timber.e(t)
                _error.postValue(t)
                _empty.postValue(date)
            }
        }
    }

    fun saveOneDayEmpty(list: List<Event>, date: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _todayEventsList.postValue(eventDao.saveOneDayEmpty(list, date))
            } catch (t: Throwable) {
                Timber.e(t)
                _error.postValue(t)
            }
        }
    }
}
