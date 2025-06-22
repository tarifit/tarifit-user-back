package com.tarifit.user.domain.port

import com.tarifit.user.domain.entity.NotificationSettings

interface NotificationSettingsRepository {
    fun findByUserId(userId: String): NotificationSettings?
    fun save(notificationSettings: NotificationSettings): NotificationSettings
    fun deleteByUserId(userId: String)
}