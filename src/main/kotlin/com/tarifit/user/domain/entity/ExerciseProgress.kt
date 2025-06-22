package com.tarifit.user.domain.entity

import java.time.LocalDateTime

data class ExerciseProgress(
    val id: String?,
    val userId: String,
    val exerciseId: String,
    val skillId: String,
    val completed: Boolean,
    val attempts: Int,
    val isCorrect: Boolean,
    val timeSpent: Long,
    val xpEarned: Int,
    val completedAt: LocalDateTime?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)