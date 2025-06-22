package com.tarifit.user.domain.entity

import java.time.LocalDateTime

data class UserProgress(
    val id: String?,
    val userId: String,
    val skillId: String,
    val completedExercises: Int,
    val totalExercises: Int,
    val skillLevel: Int,
    val isCompleted: Boolean,
    val lastExerciseDate: LocalDateTime?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)