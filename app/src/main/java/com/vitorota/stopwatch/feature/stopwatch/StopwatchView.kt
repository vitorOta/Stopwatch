package com.vitorota.stopwatch.feature.stopwatch

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.view.isVisible
import com.vitorota.stopwatch.R
import kotlinx.android.synthetic.main.view_stopwatch.view.*


class StopwatchView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) :
    LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    var seconds: Int = 0
        private set

    @ColorInt
    var color: Int = context.resources.getColor(R.color.colorAccent)
        private set

    init {
        inflate(context, R.layout.view_stopwatch, this)
        updateView()
    }

    fun setSeconds(seconds: Int) {
        this.seconds = seconds
        updateView()
    }

    fun setColor(color: Int) {
        this.color = color
        updateView()
    }


    fun updateView() {
        var firstVisible = false
        var secondVisible = false
        var thirdVisible = false

        seconds.toString().split("").filter { it.isNotEmpty() }.take(3).map { it.toInt() }.forEachIndexed { i, digit ->
            when (i) {
                0 -> {
                    stopwatch_first.setImageResource(getDigitDrawable(digit))
                    firstVisible = true
                }
                1 -> {
                    stopwatch_second.setImageResource(getDigitDrawable(digit))
                    secondVisible = true
                }
                2 -> {
                    stopwatch_third.setImageResource(getDigitDrawable(digit))
                    thirdVisible = true
                }
            }
        }

        stopwatch_first.apply {
            isVisible = firstVisible
            imageTintList = ColorStateList.valueOf(color)
        }
        stopwatch_second.apply {
            isVisible = secondVisible
            imageTintList = ColorStateList.valueOf(color)
        }
        stopwatch_third.apply {
            isVisible = thirdVisible
            imageTintList = ColorStateList.valueOf(color)
        }
        requestLayout()
        invalidate()
    }

    @DrawableRes
    private fun getDigitDrawable(digit: Int) =
        when (digit) {
            0 -> R.drawable.zero
            1 -> R.drawable.one
            2 -> R.drawable.two
            3 -> R.drawable.three
            4 -> R.drawable.four
            5 -> R.drawable.five
            6 -> R.drawable.six
            7 -> R.drawable.seven
            8 -> R.drawable.eight
            9 -> R.drawable.nine
            else -> R.drawable.zero
        }
}