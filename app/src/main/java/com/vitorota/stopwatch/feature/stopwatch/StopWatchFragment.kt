package com.vitorota.stopwatch.feature.stopwatch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.vitorota.stopwatch.R
import kotlinx.android.synthetic.main.fragment_stopwatch.*

class StopWatchFragment : Fragment() {

    private lateinit var viewModel: StopwatchViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_stopwatch, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this).get(StopwatchViewModel::class.java)

        setupView()
        setupObservers()
    }

    fun setupView() {
        bOk.setOnClickListener {
            val seconds = try {
                etValue.text.toString().toInt()
            } catch (e: NumberFormatException) {
                0
            }

            viewModel.setSeconds(seconds)
        }

        bStart.setOnClickListener { viewModel.startStopwatch() }
    }

    fun setupObservers() {
        viewModel.stopwatch.observe(this, Observer {
            vwSopwatch.seconds = it.remainingSeconds
            if (it.isStarted) {
                configViewIsStarted()
            } else {
                configViewIsStopped()
            }
        })
    }

    fun configViewIsStarted() {
        bStart.isEnabled = false
        bStart.text = getString(R.string.bStart_disabled)
    }

    fun configViewIsStopped() {
        bStart.isEnabled = true
        bStart.text = getString(R.string.bStart_enabled)
    }
}