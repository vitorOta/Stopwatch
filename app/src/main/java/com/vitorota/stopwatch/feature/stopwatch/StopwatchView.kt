package com.vitorota.stopwatch.feature.stopwatch

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import com.vitorota.stopwatch.R


class StopwatchView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) :
    LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    var seconds: Int = 0
//        set(value) {
//            seconds = value
//            updateView()
//        }

    @ColorInt
    var color: Int = Color.BLACK
        set(value) {
            color = value
            updateView()
        }

    init {
        inflate(context, R.layout.view_stopwatch, this)
    }

    private fun updateView() {
        invalidate()
        requestLayout()
//        seconds.toString().split("").take(3)?.map { it.toInt() }.forEachIndexed { i, str ->
//            when (i) {
//                0 -> stopwatch_digit0.setImageDrawable(getDigitDrawable(i))
//                1 -> stopwatch_digit1.setImageDrawable(getDigitDrawable(i))
//                2 -> stopwatch_digit2.setImageDrawable(getDigitDrawable(i))
//            }
//        }
    }

    private fun getDigitDrawable(digit: Int) = context.getDrawable(
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
    )
}