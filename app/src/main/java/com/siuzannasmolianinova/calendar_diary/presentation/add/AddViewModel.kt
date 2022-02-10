package com.siuzannasmolianinova.calendar_diary.presentation.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.siuzannasmolianinova.calendar_diary.data.db.EventDao
import com.siuzannasmolianinova.calendar_diary.data.db.entities.Event
import com.siuzannasmolianinova.calendar_diary.presentation.core.utils.LocalDateTimeConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(private val eventDao: EventDao) : ViewModel() {

    private val _success = MutableLiveData<Unit>()
    val success: LiveData<Unit> get() = _success

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable> get() = _error

    private val _cancel = MutableLiveData<Unit>()
    val cancel: LiveData<Unit> get() = _cancel

    fun cancel() {
        _cancel.postValue(Unit)
    }

    fun save(
        start: LocalDateTime,
        end: LocalDateTime,
        title: String,
        description: String,
        day: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val id = eventDao.getEventId(LocalDateTimeConverter().localDateTimeToTimestamp(start))
                _success.postValue(
                    eventDao.saveEvent(
                        Event(
//                            id = id,
                            dateFinish = end,
                            dateStart = start,
                            title = title,
                            description = description,
                            day = day
                        )
                    )
                )
            } catch (t: Throwable) {
                _error.postValue(t)
            }
        }
    }
}
