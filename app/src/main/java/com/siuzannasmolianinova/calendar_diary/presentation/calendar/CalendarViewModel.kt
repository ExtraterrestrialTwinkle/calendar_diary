package com.siuzannasmolianinova.calendar_diary.presentation.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.siuzannasmolianinova.calendar_diary.data.db.EventDao
import com.siuzannasmolianinova.calendar_diary.data.db.entities.EventExtended
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.Timestamp
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(private val eventDao: EventDao) : ViewModel() {

    private val _todayEventsList = MutableLiveData<List<EventExtended>>()
    val todayEventsList: LiveData<List<EventExtended>> get() = _todayEventsList

    private val _success = MutableLiveData<Unit>()
    val success: LiveData<Unit> get() = _success

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable> get() = _error

    fun loadOneDayList(date: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _todayEventsList.postValue(eventDao.openDay(date))
            } catch (t: Throwable) {
                _todayEventsList.postValue(emptyList())
                _error.postValue(t)
            }
        }
    }

    fun deleteEvent(eventId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _success.postValue(eventDao.deleteEvent(eventId))
            } catch(t: Throwable) {
                _error.postValue(t)
            }
        }
    }
}
