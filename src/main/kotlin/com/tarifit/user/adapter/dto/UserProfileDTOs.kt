package com.tarifit.user.adapter.dto

import java.time.LocalDateTime
import java.time.LocalTime

data class UserProfileResponse(
    val id: String?,
    val userId: String,
    val displayName: String,
    val preferredLanguage: String,
    val timezone: String,
    val profileImageUrl: String?,
    val bio: String?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)

data class UserProfileUpdateRequest(
    val displayName: String?,
    val preferredLanguage: String?,
    val timezone: String?,
    val profileImageUrl: String?,
    val bio: String?
)

data class LearningGoalsResponse(
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

data class LearningGoalsUpdateRequest(
    val dailyXpTarget: Int?,
    val weeklyLessonsTarget: Int?,
    val skillPriorities: List<String>?,
    val targetCompletionDate: LocalDateTime?
)

data class NotificationSettingsResponse(
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

data class NotificationSettingsUpdateRequest(
    val dailyReminders: Boolean?,
    val reminderTime: LocalTime?,
    val achievementAlerts: Boolean?,
    val weeklyProgress: Boolean?,
    val lessonReminders: Boolean?,
    val streakAlerts: Boolean?,
    val emailNotifications: Boolean?,
    val pushNotifications: Boolean?
)