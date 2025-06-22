package com.tarifit.user.domain.entity

import java.time.LocalDateTime

data class LearningGoals(
    val id: String?,
    val userId: String,
    val dailyXpTarget: Int,
    val weeklyLessonsTarget: Int,
    val skillPriorities: List<String>,
    val targetCompletionDate: LocalDateTime?,
    val isActive: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)