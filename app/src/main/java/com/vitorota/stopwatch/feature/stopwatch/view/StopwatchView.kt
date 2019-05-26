package com.vitorota.stopwatch.feature.stopwatch.view

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
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

    var digitSize: DigitSize = DigitSize.EXTRA_LARGE
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

    fun setDigitSize(digitSize: DigitSize) {
        this.digitSize = digitSize
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

        val digitWidth = context.resources.getDimensionPixelSize(getDigitWidthDimen())
        val digitHeight = context.resources.getDimensionPixelSize(getDigitHeightDimen())

        configDigit(stopwatch_first, firstVisible, digitWidth, digitHeight)
        configDigit(stopwatch_second, secondVisible, digitWidth, digitHeight)
        configDigit(stopwatch_third, thirdVisible, digitWidth, digitHeight)

        requestLayout()
        invalidate()
    }

    /**
     * @param digitWidth in px
     * @param digitHeight in px
     * */
    private fun configDigit(imageView: ImageView, isVisible: Boolean, digitWidth: Int, digitHeight: Int) {
        imageView.apply {
            this.isVisible = isVisible
            imageTintList = ColorStateList.valueOf(color)
            updateLayoutParams<LayoutParams> {
                width = digitWidth
                height = digitHeight
            }
        }
    }

    private fun getDigitWidthDimen() =
        when (digitSize) {
            DigitSize.EXTRA_SMALL -> R.dimen.w_extraSmall
            DigitSize.SMALL -> R.dimen.w_small
            DigitSize.MEDIUM -> R.dimen.w_medium
            DigitSize.LARGE -> R.dimen.w_large
            DigitSize.EXTRA_LARGE -> R.dimen.w_extraLarge
        }


    private fun getDigitHeightDimen() =
        when (digitSize) {
            DigitSize.EXTRA_SMALL -> R.dimen.h_extraSmall
            DigitSize.SMALL -> R.dimen.h_small
            DigitSize.MEDIUM -> R.dimen.h_medium
            DigitSize.LARGE -> R.dimen.h_large
            DigitSize.EXTRA_LARGE -> R.dimen.h_extraLarge
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