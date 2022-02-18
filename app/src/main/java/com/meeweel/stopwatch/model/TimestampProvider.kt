package com.meeweel.stopwatch.model

interface TimestampProvider {
    fun getMilliseconds(): Long
}