package com.tarifit.user.adapter.dto

import java.time.LocalDateTime

data class UserAchievementResponse(
    val id: String?,
    val userId: String,
    val achievementId: String,
    val unlockedAt: LocalDateTime?,
    val progress: Int,
    val isUnlocked: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)

data class AchievementCheckResponse(
    val achievementId: String,
    val isUnlocked: Boolean,
    val progress: Int,
    val justUnlocked: Boolean,
    val xpRewarded: Int
)