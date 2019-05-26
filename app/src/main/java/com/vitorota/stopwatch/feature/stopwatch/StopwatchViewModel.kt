package com.vitorota.stopwatch.feature.stopwatch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vitorota.stopwatch.feature.stopwatch.view.DigitSize
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StopwatchViewModel : ViewModel() {
    private val _stopwatch = MutableLiveData<Stopwatch>()
    val stopwatch: LiveData<Stopwatch> = _stopwatch

    private val _color = MutableLiveData<Int>()
    /** the color of the Stopwatch */
    val color: LiveData<Int> = _color

    private val _size = MutableLiveData<DigitSize>()
    /** the size (in px) of the Stopwatch */
    val size: LiveData<DigitSize> = _size

    init {
        _stopwatch.value =
            Stopwatch()
    }


    /**
     * set the color of the Stopwatch
     */
    fun setColor(color: Int) {
        this._color.value = color
    }

    /**
     * set the size of the Stopwatch
     */
    fun setSize(size: DigitSize) {
        this._size.value = size
    }

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