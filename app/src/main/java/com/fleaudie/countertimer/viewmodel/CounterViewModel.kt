package com.fleaudie.countertimer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fleaudie.countertimer.model.Counter
import java.util.Timer
import kotlin.concurrent.timerTask

class CounterViewModel : ViewModel() {
    private val _counter = MutableLiveData<Int>()
    val counter: LiveData<Int> = _counter
    private var timer: Timer? = null

    fun startTimer(durationInSeconds: Int) {
        if (timer == null) {
            _counter.value = durationInSeconds
            timer = Timer()
            timer?.scheduleAtFixedRate(timerTask {
                val currentCount = _counter.value ?: 0
                if (currentCount > 0) {
                    _counter.postValue(currentCount - 1)
                } else {
                    stopTimer()
                }
            }, 0, 1000)
        }
    }

    fun stopTimer() {
        timer?.cancel()
        timer = null
    }

    override fun onCleared() {
        super.onCleared()
        stopTimer()
    }
}