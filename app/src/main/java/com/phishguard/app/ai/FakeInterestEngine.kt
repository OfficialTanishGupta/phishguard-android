package com.phishguard.app.ai

import kotlin.random.Random

class FakeInterestEngine {

    private val fakeInterestPool = listOf(
        "Luxury Watches",
        "Crypto Trading",
        "Online Dating",
        "Fitness Supplements",
        "Stock Market Tips",
        "Gaming Accessories",
        "Travel Vlogs",
        "Premium Courses",
        "AI Tools",
        "Freelancing Platforms"
    )

    fun generateFakeInterests(
        count: Int = 3
    ): List<String> {
        return fakeInterestPool.shuffled()
            .take(count)
    }

    fun generateInterestPayload(
        appPackage: String
    ): FakeInterestPayload {
        return FakeInterestPayload(
            sourceApp = appPackage,
            interests = generateFakeInterests(),
            confidence = Random.nextDouble(0.6, 0.95)
        )
    }
}
