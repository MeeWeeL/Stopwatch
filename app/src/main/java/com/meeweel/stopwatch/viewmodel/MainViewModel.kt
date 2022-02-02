package com.meeweel.stopwatch.viewmodel

import kotlinx.coroutines.flow.collect
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.meeweel.stopwatch.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class MainViewModel(
    private val timestampProvider: TimestampProvider = TimestampProviderImpl()
) : ViewModel() {
    private val firstLiveDataForViewToObserve: MutableLiveData<String> = MutableLiveData()
    private val secondLiveDataForViewToObserve: MutableLiveData<String> = MutableLiveData()

    private val firstLiveData: LiveData<String> = firstLiveDataForViewToObserve as LiveData<String>
    private val secondLiveData: LiveData<String> = secondLiveDataForViewToObserve as LiveData<String>

    fun getFirstData() = firstLiveData
    fun getSecondData() = secondLiveData


    init {
        CoroutineScope(
            Dispatchers.Main
                    + SupervisorJob()
        ).launch {
            stopwatchListOrchestrator.ticker.collect {
                firstLiveDataForViewToObserve.postValue(it)
            }
        }

        CoroutineScope(
            Dispatchers.Main
                    + SupervisorJob()
        ).launch {
            secondStopwatchListOrchestrator.ticker.collect {
                secondLiveDataForViewToObserve.postValue(it)
            }
        }
    }

    private val stopwatchListOrchestrator = StopwatchListOrchestrator(
        StopwatchStateHolder(
            StopwatchStateCalculator(
                timestampProvider,
                ElapsedTimeCalculator(timestampProvider)
            ),
            ElapsedTimeCalculator(timestampProvider),
            TimestampMillisecondsFormatter()
        ),
        CoroutineScope(
            Dispatchers.Main
                    + SupervisorJob()
        )
    )

    private val secondStopwatchListOrchestrator = StopwatchListOrchestrator(
        StopwatchStateHolder(
            StopwatchStateCalculator(
                timestampProvider,
                ElapsedTimeCalculator(timestampProvider)
            ),
            ElapsedTimeCalculator(timestampProvider),
            TimestampMillisecondsFormatter()
        ),
        CoroutineScope(
            Dispatchers.Main
                    + SupervisorJob()
        )
    )

    fun start1() {
        stopwatchListOrchestrator.start()
    }
    fun pause1() {
        stopwatchListOrchestrator.pause()
    }
    fun stop1() {
        stopwatchListOrchestrator.stop()
    }
    fun start2() {
        secondStopwatchListOrchestrator.start()
    }
    fun pause2() {
        secondStopwatchListOrchestrator.pause()
    }
    fun stop2() {
        secondStopwatchListOrchestrator.stop()
    }

}