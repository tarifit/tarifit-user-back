package com.tarifit.user.domain.entity

import java.time.LocalDate
import java.time.LocalDateTime

data class UserStats(
    val id: String?,
    val userId: String,
    val totalXP: Int,
    val currentLevel: Int,
    val currentStreak: Int,
    val longestStreak: Int,
    val lastStudyDate: LocalDate?,
    val totalStudyTime: Long,
    val exercisesCompleted: Int,
    val skillsCompleted: Int,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)