package com.fleaudie.countertimer.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fleaudie.countertimer.model.Counter
import java.util.Timer
import kotlin.concurrent.timerTask

class CounterViewModel : ViewModel() {
    private var counter = MutableLiveData<Int>()
    private var timer: Timer? = null

    fun getCounter() = counter

    fun startTimer(durationInSeconds: Int) {
        if (timer == null) {
            counter.value = durationInSeconds
            timer = Timer()
            timer?.scheduleAtFixedRate(timerTask {
                val currentCount = counter.value ?: 0
                if (currentCount > 0) {
                    counter.postValue(currentCount - 1)
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