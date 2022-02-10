package com.siuzannasmolianinova.calendar_diary.presentation.add

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.siuzannasmolianinova.calendar_diary.R
import com.siuzannasmolianinova.calendar_diary.databinding.AddFragmentBinding
import com.siuzannasmolianinova.calendar_diary.presentation.core.BaseFragment
import com.siuzannasmolianinova.calendar_diary.presentation.core.utils.LocalDateTimeConverter
import com.siuzannasmolianinova.calendar_diary.presentation.core.utils.showSnackbar
import dagger.hilt.android.AndroidEntryPoint
import org.threeten.bp.LocalDateTime

@AndroidEntryPoint
class AddFragment : BaseFragment<AddFragmentBinding>(AddFragmentBinding::inflate) {

    private val viewModel: AddViewModel by viewModels()
    private val args: AddFragmentArgs by navArgs()
    private lateinit var startTime: LocalDateTime
    private lateinit var finishTime: LocalDateTime

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startTime = LocalDateTimeConverter().dayTime(args.timestamp)
        finishTime = LocalDateTimeConverter().dayTime(args.timestamp + 3_600_000)

        initToolbar()
        bindViewModel()
        binding.cancelButton.setOnClickListener {
            viewModel.cancel()
        }
        binding.saveButton.setOnClickListener {
            saveEvent()
        }
    }

    private fun initToolbar() {
        val toolbar = binding.appbar.toolbar
        toolbar.title = getString(R.string.add_event)
    }

    private fun bindViewModel() {
        viewModel.success.observe(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
        viewModel.cancel.observe(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
        viewModel.error.observe(viewLifecycleOwner) {
            showSnackbar(binding.root, binding.barrier3, it.message.toString())
        }
    }

    private fun saveEvent() {
        val title = binding.titleTextField.editText?.text.toString()
        val description = binding.descriptionTextField.editText?.text.toString()

        viewModel.save(
            title = title,
            description = description,
            start = startTime,
            end = finishTime,
            day = LocalDateTimeConverter().day(startTime)
        )
    }
}
