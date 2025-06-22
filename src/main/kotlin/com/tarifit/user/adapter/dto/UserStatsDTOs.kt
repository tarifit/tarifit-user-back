package com.tarifit.user.adapter.dto

import java.time.LocalDate
import java.time.LocalDateTime

data class UserStatsResponse(
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

data class DashboardStatsResponse(
    val totalXP: Int,
    val currentLevel: Int,
    val currentStreak: Int,
    val longestStreak: Int,
    val exercisesCompleted: Int,
    val skillsCompleted: Int,
    val totalStudyTime: Long,
    val weeklyXP: Int,
    val averageSessionTime: Long,
    val lastStudyDate: LocalDate?
)

data class AddXPRequest(
    val amount: Int,
    val source: String
)

data class AddXPResponse(
    val totalXP: Int,
    val currentLevel: Int,
    val xpAdded: Int,
    val levelUp: Boolean
)

data class StudySessionResponse(
    val id: String?,
    val userId: String,
    val date: LocalDate,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime?,
    val xpEarned: Int,
    val exercisesCompleted: Int,
    val isActive: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)

data class EndSessionRequest(
    val xpEarned: Int,
    val exercisesCompleted: Int
)