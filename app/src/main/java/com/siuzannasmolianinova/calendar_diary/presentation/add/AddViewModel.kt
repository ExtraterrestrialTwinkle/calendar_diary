package com.siuzannasmolianinova.calendar_diary.presentation.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.siuzannasmolianinova.calendar_diary.data.db.EventDao
import com.siuzannasmolianinova.calendar_diary.data.db.entities.EventExtended
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(private val eventDao: EventDao) : ViewModel() {

    private val _success = MutableLiveData<Unit>()
    val success: LiveData<Unit> get() = _success

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable> get() = _error

    fun cancel(){

    }

    fun save(
        start: LocalDateTime,
        end: LocalDateTime,
        title: String,
        description: String,
        day: Long
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _success.postValue(
                    eventDao.saveEvent(
                        EventExtended(
                            id = 0,
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
