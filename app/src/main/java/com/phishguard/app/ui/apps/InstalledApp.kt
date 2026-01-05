package com.phishguard.app.ui.apps

import android.graphics.drawable.Drawable

data class InstalledApp(
    val name: String,
    val packageName: String,
    val icon: android.graphics.drawable.Drawable,
    val enabled: Boolean = false
)

