package com.tarifit.user.domain.entity

import java.time.LocalDateTime
import java.time.LocalTime

data class NotificationSettings(
    val id: String?,
    val userId: String,
    val dailyReminders: Boolean,
    val reminderTime: LocalTime?,
    val achievementAlerts: Boolean,
    val weeklyProgress: Boolean,
    val lessonReminders: Boolean,
    val streakAlerts: Boolean,
    val emailNotifications: Boolean,
    val pushNotifications: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)