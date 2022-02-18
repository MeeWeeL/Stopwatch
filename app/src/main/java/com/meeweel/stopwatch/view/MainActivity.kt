package com.meeweel.stopwatch.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.meeweel.stopwatch.databinding.ActivityMainBinding
import com.meeweel.stopwatch.viewmodel.MainViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val model: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private val listener: View.OnClickListener = View.OnClickListener {
        with(binding) {
            when (it.id) {
                buttonStart.id -> model.start1()
                buttonPause.id -> model.pause1()
                buttonStop.id -> model.stop1()
                secondButtonStart.id -> model.start2()
                secondButtonPause.id -> model.pause2()
                secondButtonStop.id -> model.stop2()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model.getFirstData().observe(this, {
            binding.textTime.text = it
        })
        model.getSecondData().observe(this, {
            binding.secondTextTime.text = it
        })

        with(binding) {
            buttonStart.setOnClickListener(listener)
            buttonPause.setOnClickListener(listener)
            buttonStop.setOnClickListener(listener)
            secondButtonStart.setOnClickListener(listener)
            secondButtonPause.setOnClickListener(listener)
            secondButtonStop.setOnClickListener(listener)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        with(binding) {
            buttonStart.setOnClickListener(null)
            buttonPause.setOnClickListener(null)
            buttonStop.setOnClickListener(null)
            secondButtonStart.setOnClickListener(null)
            secondButtonPause.setOnClickListener(null)
            secondButtonStop.setOnClickListener(null)
        }
    }
}
