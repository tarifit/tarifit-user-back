package com.tarifit.user.domain.entity

import java.time.LocalDateTime

data class UserAchievement(
    val id: String?,
    val userId: String,
    val achievementId: String,
    val unlockedAt: LocalDateTime?,
    val progress: Int,
    val isUnlocked: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)