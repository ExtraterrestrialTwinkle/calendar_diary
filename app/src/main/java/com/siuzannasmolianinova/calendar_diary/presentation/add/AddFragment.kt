package com.siuzannasmolianinova.calendar_diary.presentation.add

import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_KEYBOARD
import com.google.android.material.timepicker.TimeFormat
import com.siuzannasmolianinova.calendar_diary.data.LocalDateTimeConverter
import com.siuzannasmolianinova.calendar_diary.databinding.AddFragmentBinding
import com.siuzannasmolianinova.calendar_diary.presentation.core.BaseFragment
import dagger.hilt.EntryPoint
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId

class AddFragment : BaseFragment<AddFragmentBinding>(AddFragmentBinding::inflate) {

    private val viewModel: AddViewModel by viewModels()
    private val args: AddFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val startDayTime = dayTime(args.timestamp)
        val endDayTime = dayTime(args.timestamp + 3_600_000)
        binding.startDateTextView.text =
            LocalDateTimeConverter().localDateTimeToDateTime(startDayTime)[0]
        binding.endDateTextView.text =
            LocalDateTimeConverter().localDateTimeToDateTime(endDayTime)[0]
        binding.startTimeTextView.text =
            LocalDateTimeConverter().localDateTimeToDateTime(startDayTime)[1]
        binding.endTimeTextView.text =
            LocalDateTimeConverter().localDateTimeToDateTime(endDayTime)[1]

        binding.startDateTextView.setOnClickListener {
            showDatePicker(START_FLAG)
        }

        binding.endDateTextView.setOnClickListener {
            showDatePicker(END_FLAG)
        }

        binding.startTimeTextView.setOnClickListener {
            showTimePicker(START_FLAG)
        }

        binding.endTimeTextView.setOnClickListener {
            showTimePicker(END_FLAG)
        }

        binding.cancelButton.setOnClickListener {
            viewModel.cancel()
        }

        binding.saveButton.setOnClickListener {
            saveEvent()
        }
    }

    private fun saveEvent() {
        val title = binding.titleTextField.editText?.text.toString()
        val description = binding.descriptionTextField.editText?.text.toString()
        val startDate = binding.startDateTextView.text.toString()
        val startTime = binding.startTimeTextView.text.toString()
        val endDate = binding.endDateTextView.text.toString()
        val endTime = binding.endTimeTextView.text.toString()

        // viewModel.save(title = title, description = description, start = )
    }

    private fun showDatePicker(flag: Int) {
    }

    private fun showTimePicker(flag: Int) {
        var hour: Int? = null
        var minute: Int? = null
        when (flag) {
            0 -> {
                hour =
                    LocalDateTimeConverter().localDateTimeToHoursMinutes(dayTime(args.timestamp))[0].toInt()
                minute =
                    LocalDateTimeConverter().localDateTimeToHoursMinutes(dayTime(args.timestamp))[1].toInt()
            }
            1 -> {
                hour =
                    LocalDateTimeConverter().localDateTimeToHoursMinutes(dayTime(args.timestamp + 3_600_000))[0].toInt()
                minute =
                    LocalDateTimeConverter().localDateTimeToHoursMinutes(dayTime(args.timestamp + 3_600_000))[1].toInt()
            }
        }
        val isSystem24Hour = is24HourFormat(requireContext())
        val clockFormat = if (isSystem24Hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H
        val picker = MaterialTimePicker.Builder()
            .setInputMode(INPUT_MODE_KEYBOARD)
            .setTimeFormat(clockFormat)
            .setHour(hour ?: 0)
            .setMinute(minute ?: 0)
            .build()

        picker.addOnPositiveButtonClickListener {
            when (flag) {
                0 ->
                    binding.startTimeTextView.text =
                        StringBuilder().append(picker.hour).append(":").append(picker.minute)
                1 ->
                    binding.endTimeTextView.text =
                        StringBuilder().append(picker.hour).append(":").append(picker.minute)
            }
        }
        picker.addOnNegativeButtonClickListener {
            picker.dismiss()
        }
        picker.show(childFragmentManager, "TimePicker")
    }

    private fun dayTime(timeStamp: Long): LocalDateTime =
        LocalDateTime.ofInstant(Instant.ofEpochMilli(timeStamp), ZoneId.systemDefault())

    companion object {
        private const val START_FLAG = 0
        private const val END_FLAG = 1
    }
}
//            { _, year, month, dayOfMonth ->
//                TimePickerDialog(
//                    requireContext(),
//                    { _, hourOfDay, minute ->
//                        val zonedDateTime = LocalDateTime.of(year, month + 1, dayOfMonth, hourOfDay, minute)
//                            .atZone(ZoneId.systemDefault())
//
//                        Toast.makeText(
//                            requireContext(),
//                            "Выбрано время: $zonedDateTime",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                        selectedInstant = zonedDateTime.toInstant()
//
//                        Log.d("selectedInstant", "$selectedInstant")
//                        Log.d("locationInfo1", "$locationInfo")
//
//                        var newList = arrayListOf<LocationInfo>()
//                        newList = arrayListOf<LocationInfo>()
//                        locationInfo.forEach { element -> newList.add(element.copy()) }
//                        val selectedInfo = newList.elementAt(position)
//                        newList.removeAt(position)
//                        selectedInfo.timestamp = selectedInstant as Instant
//                        selectedInfo.id = Random.nextLong()
//                        newList.add(position, selectedInfo)
//                        Log.d("timestamp", "${newList[position].timestamp}")
//
//                        locationInfoAdapter?.setList(newList)
//                        Log.d("locationInfo3 = newList", "$newList")
//                        Log.d("locationInfo1 = locationInfo", "$locationInfo")
//
//                        // locationInfoAdapter?.notifyItemChanged(position) // Почему только так работает???
//                        state = LocationInfoState(newList)
//                    },
//                    currentDateTime.hour,
//                    currentDateTime.minute,
//                    true
//                )
//                    .show()
//            },
//            currentDateTime.year,
//            currentDateTime.month.value - 1,
//            currentDateTime.dayOfMonth
//            )
