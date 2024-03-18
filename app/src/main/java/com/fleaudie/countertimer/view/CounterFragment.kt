package com.fleaudie.countertimer.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.fleaudie.countertimer.databinding.FragmentCounterBinding
import com.fleaudie.countertimer.viewmodel.CounterViewModel

class CounterFragment : Fragment() {
    private var _binding: FragmentCounterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CounterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCounterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val hourPicker = binding.hourPicker
        val minutePicker = binding.minutePicker
        val secondPicker = binding.secondPicker
        val btnStart = binding.btnStart
        val btnStop = binding.btnStop

        hourPicker.minValue = 0
        hourPicker.maxValue = 23

        minutePicker.minValue = 0
        minutePicker.maxValue = 59

        secondPicker.minValue = 0
        secondPicker.maxValue = 59

        btnStart.setOnClickListener {
            val hour = hourPicker.value
            val minute = minutePicker.value
            val second = secondPicker.value
            val durationInSeconds = hour * 3600 + minute * 60 + second
            viewModel.startTimer(durationInSeconds)
        }

        btnStop.setOnClickListener {
            viewModel.stopTimer()
        }

        viewModel.counter.observe(viewLifecycleOwner) { counterValue ->
            val hours = counterValue / 3600
            val minutes = (counterValue % 3600) / 60
            val seconds = counterValue % 60

            hourPicker.value = hours
            minutePicker.value = minutes
            secondPicker.value = seconds
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}