package com.tarifit.user.domain.entity

import java.time.LocalDate
import java.time.LocalDateTime

data class StudySession(
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