package com.tarifit.user.infrastructure.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.index.Indexed
import java.time.LocalDateTime
import java.time.LocalTime

@Document(collection = "notification_settings")
data class NotificationSettingsEntity(
    @Id
    val id: String? = null,
    
    @Indexed(unique = true)
    val userId: String,
    
    val dailyReminders: Boolean,
    val reminderTime: LocalTime? = null,
    val achievementAlerts: Boolean,
    val weeklyProgress: Boolean,
    val lessonReminders: Boolean,
    val streakAlerts: Boolean,
    val emailNotifications: Boolean,
    val pushNotifications: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)