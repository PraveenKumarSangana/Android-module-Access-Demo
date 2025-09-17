package com.example.androidmoduleaccessdemo.common.component

import android.annotation.SuppressLint
import com.example.androidmoduleaccessdemo.model.User
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class AccessManagerComponent(private val user: User) {

    fun getCoolingTimerText(): String? {
        val startTime = user.coolingStartTime ?: return null
        val endTime = user.coolingEndTime ?: return null

        return try {
            val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            formatter.timeZone = TimeZone.getTimeZone("UTC")

            val coolingStart = formatter.parse(startTime)
            val coolingEnd = formatter.parse(endTime)
            val now = Date()

            when {
                now.before(coolingStart) -> {
                    null
                }
                now.before(coolingEnd) -> {
                    "Cooling period ends in: ${coolingEnd?.let { getTimeDifference(now, it) }}"
                }
                else -> {
                    null
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    @SuppressLint("DefaultLocale")
    private fun getTimeDifference(now: Date, target: Date): String {
        val diff = target.time - now.time
        val seconds = diff / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        return String.format("%02d:%02d:%02d", hours, minutes % 60, seconds % 60)
    }
}

