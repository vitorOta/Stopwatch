package com.vitorota.stopwatch.feature.stopwatch

data class Stopwatch(
    var isStarted: Boolean = false,
    var initialSeconds: Int = 0,
    var remainingSeconds: Int = initialSeconds
) {
    fun resetSeconds() {
        remainingSeconds = initialSeconds
    }

    fun tick() {
        remainingSeconds--
    }
}