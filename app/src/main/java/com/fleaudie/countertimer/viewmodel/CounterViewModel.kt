package com.fleaudie.countertimer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CounterViewModel : ViewModel() {
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private var _counter = MutableLiveData<Int>()
    val counter: LiveData<Int>
        get() = _counter

    private var currentJob: Job? = null

    fun startTimer(durationInSeconds: Int) {
        currentJob?.cancel()

        _counter.value = durationInSeconds
        currentJob = uiScope.launch {
            while (_counter.value!! > 0) {
                delay(1000)
                _counter.value = _counter.value!! - 1
            }
        }
    }

    fun stopTimer() {
        currentJob?.cancel()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}