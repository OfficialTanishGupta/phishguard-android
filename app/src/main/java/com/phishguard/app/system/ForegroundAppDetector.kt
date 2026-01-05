package com.phishguard.app.system

import android.app.usage.UsageEvents
import android.app.usage.UsageStatsManager
import android.content.Context

class ForegroundAppDetector(private val context: Context) {

    private var lastValidApp: String? = null

    fun getForegroundApp(): String? {
        val usageStatsManager =
            context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager

        val endTime = System.currentTimeMillis()
        val startTime = endTime - 10_000

        val usageEvents = usageStatsManager.queryEvents(startTime, endTime)
        val event = UsageEvents.Event()

        var latestPackage: String? = null
        var latestTimestamp = 0L

        while (usageEvents.hasNextEvent()) {
            usageEvents.getNextEvent(event)

            if (event.eventType == UsageEvents.Event.MOVE_TO_FOREGROUND) {
                if (event.timeStamp > latestTimestamp) {
                    latestTimestamp = event.timeStamp
                    latestPackage = event.packageName
                }
            }
        }

        // ❌ Ignore own app
        if (latestPackage == context.packageName) {
            return lastValidApp
        }

        // ❌ Ignore launcher
        if (latestPackage?.contains("launcher", ignoreCase = true) == true) {
            return lastValidApp
        }

        // ✅ Update stable foreground app
        if (latestPackage != null) {
            lastValidApp = latestPackage
        }

        return lastValidApp
    }
}
