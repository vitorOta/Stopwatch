package com.vitorota.stopwatch.feature.stopwatch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StopwatchViewModel : ViewModel() {
    private val _stopwatch = MutableLiveData<Stopwatch>().apply {
        value =
            Stopwatch()
    }
    val stopwatch: LiveData<Stopwatch> = _stopwatch


    fun setSeconds(seconds: Int) {
        _stopwatch.value = _stopwatch.value?.apply {
            initialSeconds = seconds
            resetSeconds()
        }
    }

    fun startStopwatch() {
        _stopwatch.value = _stopwatch.value?.apply {
            isStarted = true
            resetSeconds()
        }
        viewModelScope.launch {

            stopwatch.value?.run {
                while (remainingSeconds > 0) {
                    subtractOneSecond()
                }
            }
            finishStopWatch()
        }
    }

    private fun finishStopWatch() {
        _stopwatch.value = _stopwatch.value?.apply { isStarted = false }
    }

    suspend fun subtractOneSecond() = withContext(Dispatchers.Default) {
        Thread.sleep(1_000)
        _stopwatch.postValue(_stopwatch.value?.apply { tick() })
    }
}