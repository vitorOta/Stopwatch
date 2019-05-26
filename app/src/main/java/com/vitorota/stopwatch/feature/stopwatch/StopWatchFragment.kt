package com.vitorota.stopwatch.feature.stopwatch

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.SeekBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.divyanshu.colorseekbar.ColorSeekBar
import com.vitorota.stopwatch.R
import com.vitorota.stopwatch.feature.stopwatch.view.DigitSize
import kotlinx.android.synthetic.main.fragment_stopwatch.*

class StopWatchFragment : Fragment() {

    private val viewModel: StopwatchViewModel by lazy {
        ViewModelProviders.of(this).get(StopwatchViewModel::class.java)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_stopwatch, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupView()
        setupObservers()
    }

    private fun setupView() {
        //buttons
        bOk.setOnClickListener {
            val seconds = try {
                etValue.text.toString().toInt()
            } catch (e: NumberFormatException) {
                0
            }

            viewModel.setSeconds(seconds)
        }

        bStart.setOnClickListener { viewModel.startStopwatch() }

        bColor.setOnClickListener { toggleColorPicker() }

        bSize.setOnClickListener { toogleSeekBarSize() }

        //others
        colorPicker.setOnColorChangeListener(object : ColorSeekBar.OnColorChangeListener {
            override fun onColorChangeListener(color: Int) {
                viewModel.setColor(color)
            }

        })

        sbSize.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

                val digitSize = when (progress) {
                    0 -> DigitSize.EXTRA_SMALL
                    1 -> DigitSize.SMALL
                    2 -> DigitSize.MEDIUM
                    3 -> DigitSize.LARGE
                    4 -> DigitSize.EXTRA_LARGE
                    else -> DigitSize.EXTRA_LARGE
                }

                //update size in viewModel
                viewModel.setSize(digitSize)
            }

        })

        etValue.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                bOk.performClick()
            }
            false
        }
    }

    private fun setupObservers() {
        viewModel.stopwatch.observe(this, Observer {
            vwStopwatch.setSeconds(it.remainingSeconds)
            if (it.isStarted) {
                configViewIsStarted()
            } else {
                configViewIsStopped()
            }
        })

        viewModel.color.observe(this, Observer {
            vwStopwatch.setColor(it)
        })

        viewModel.size.observe(this, Observer {
            vwStopwatch.setDigitSize(it)
        })
    }

    private fun configViewIsStarted() {
        bStart.isEnabled = false
        bStart.text = getString(R.string.bStart_disabled)
    }

    private fun configViewIsStopped() {
        bStart.isEnabled = true
        bStart.text = getString(R.string.bStart_enabled)
    }

    private fun toggleColorPicker() {
        colorPicker.isVisible = !colorPicker.isVisible
        bColor.imageTintList = ColorStateList.valueOf(
            context!!.resources.getColor(
                if (colorPicker.isVisible) R.color.colorAccent else android.R.color.black
            )
        )
    }

    private fun toogleSeekBarSize() {
        sbSize.isVisible = !sbSize.isVisible
        bSize.imageTintList = ColorStateList.valueOf(
            context!!.resources.getColor(
                if (sbSize.isVisible) R.color.colorAccent else android.R.color.black
            )
        )

        val options = context!!.resources.getStringArray(R.array.size_options)
    }
}