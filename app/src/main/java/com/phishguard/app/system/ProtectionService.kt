package com.phishguard.app.system

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.phishguard.app.data.AppSelectionDataStore
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest
import com.phishguard.app.ai.FakeInterestEngine


class ProtectionService : Service() {

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    private lateinit var detector: ForegroundAppDetector
    private lateinit var dataStore: AppSelectionDataStore

    private var selectedApps: Set<String> = emptySet()

    private val fakeInterestEngine = FakeInterestEngine()


    override fun onCreate() {
        super.onCreate()
        detector = ForegroundAppDetector(this)
        dataStore = AppSelectionDataStore(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        startForeground(
            NOTIFICATION_ID,
            createNotification("PhishGuard active")
        )

        observeSelectedApps()
        startMonitoring()

        return START_STICKY
    }

    private fun observeSelectedApps() {
        serviceScope.launch {
            dataStore.selectedApps.collectLatest {
                selectedApps = it
            }
        }
    }

    private fun startMonitoring() {
        serviceScope.launch {
            while (isActive) {
                val currentApp = detector.getForegroundApp()

                if (currentApp != null && selectedApps.contains(currentApp)) {

                    // 🧠 Generate fake interest payload
                    val payload =
                        fakeInterestEngine.generateInterestPayload(currentApp)

                    // 🔍 Temporary logging (for verification)
                    android.util.Log.d(
                        "PhishGuard-AI",
                        "FakeInterest → $payload"
                    )

                    updateNotification("Protecting: $currentApp")

                } else {
                    updateNotification("PhishGuard active")
                }


                delay(2000)
            }
        }
    }

    override fun onDestroy() {
        serviceScope.cancel()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun updateNotification(text: String) {
        val manager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(NOTIFICATION_ID, createNotification(text))
    }

    private fun createNotification(text: String): Notification {
        val channelId = "phishguard_protection"

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "PhishGuard Protection",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }

        return NotificationCompat.Builder(this, channelId)
            .setContentTitle("PhishGuard")
            .setContentText(text)
            .setSmallIcon(android.R.drawable.ic_lock_idle_lock)
            .setOngoing(true)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .build()
    }

    companion object {
        private const val NOTIFICATION_ID = 1001
    }
}
