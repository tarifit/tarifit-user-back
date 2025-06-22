package com.tarifit.user.adapter.dto

import java.time.LocalDateTime

data class UserProgressSummaryResponse(
    val totalSkills: Int,
    val completedSkills: Int,
    val totalExercises: Int,
    val completedExercises: Int,
    val overallProgress: Int,
    val skillProgresses: List<UserProgressResponse>
)

data class UserProgressResponse(
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

data class ExerciseCompletionRequest(
    val exerciseId: String,
    val skillId: String,
    val isCorrect: Boolean,
    val timeSpent: Long
)

data class ExerciseCompletionResponse(
    val exerciseId: String,
    val skillId: String,
    val isCorrect: Boolean,
    val xpEarned: Int,
    val attempts: Int,
    val isFirstAttempt: Boolean,
    val timeSpent: Long
)

data class ExerciseProgressResponse(
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