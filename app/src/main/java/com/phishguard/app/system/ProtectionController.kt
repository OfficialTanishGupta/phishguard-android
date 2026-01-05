package com.phishguard.app.system

class ProtectionController {

    private var active = false

    fun startProtection(appPackage: String) {
        if (!active) {
            active = true
            // TEMP: log / placeholder
            println("🛡️ Protection STARTED for $appPackage")
        }
    }

    fun stopProtection() {
        if (active) {
            active = false
            println("🛑 Protection STOPPED")
        }
    }

    fun isActive(): Boolean = active
}
