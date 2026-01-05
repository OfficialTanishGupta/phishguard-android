package com.phishguard.app.ai

data class FakeInterestPayload(
    val sourceApp: String,
    val interests: List<String>,
    val confidence: Double,
    val timestamp: Long = System.currentTimeMillis()
)
