package com.tarifit.user.application

import com.tarifit.user.domain.entity.LearningGoals
import com.tarifit.user.domain.entity.NotificationSettings
import com.tarifit.user.domain.entity.UserProfile
import com.tarifit.user.domain.port.LearningGoalsRepository
import com.tarifit.user.domain.port.NotificationSettingsRepository
import com.tarifit.user.domain.port.UserProfileRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.LocalTime

@Service
class UserProfileService(
    private val userProfileRepository: UserProfileRepository,
    private val learningGoalsRepository: LearningGoalsRepository,
    private val notificationSettingsRepository: NotificationSettingsRepository
) {
    private val logger = LoggerFactory.getLogger(UserProfileService::class.java)

    fun getOrCreateProfile(userId: String): UserProfile {
        logger.debug("Getting or creating profile for user: $userId")
        
        return userProfileRepository.findByUserId(userId) ?: run {
            logger.info("Creating new profile for user: $userId")
            val defaultProfile = createDefaultProfile(userId)
            userProfileRepository.save(defaultProfile)
        }
    }

    fun updateProfile(userId: String, displayName: String?, preferredLanguage: String?, timezone: String?, profileImageUrl: String?, bio: String?): UserProfile {
        logger.debug("Updating profile for user: $userId")
        
        val existingProfile = getOrCreateProfile(userId)
        val updatedProfile = existingProfile.copy(
            displayName = displayName ?: existingProfile.displayName,
            preferredLanguage = preferredLanguage ?: existingProfile.preferredLanguage,
            timezone = timezone ?: existingProfile.timezone,
            profileImageUrl = profileImageUrl ?: existingProfile.profileImageUrl,
            bio = bio ?: existingProfile.bio,
            updatedAt = LocalDateTime.now()
        )
        
        return userProfileRepository.save(updatedProfile)
    }

    fun getLearningGoals(userId: String): LearningGoals {
        logger.debug("Getting learning goals for user: $userId")
        
        return learningGoalsRepository.findByUserId(userId) ?: run {
            logger.info("Creating default learning goals for user: $userId")
            val defaultGoals = createDefaultLearningGoals(userId)
            learningGoalsRepository.save(defaultGoals)
        }
    }

    fun updateLearningGoals(userId: String, dailyXpTarget: Int?, weeklyLessonsTarget: Int?, skillPriorities: List<String>?, targetCompletionDate: LocalDateTime?): LearningGoals {
        logger.debug("Updating learning goals for user: $userId")
        
        validateLearningGoals(dailyXpTarget, weeklyLessonsTarget)
        
        val existingGoals = getLearningGoals(userId)
        val updatedGoals = existingGoals.copy(
            dailyXpTarget = dailyXpTarget ?: existingGoals.dailyXpTarget,
            weeklyLessonsTarget = weeklyLessonsTarget ?: existingGoals.weeklyLessonsTarget,
            skillPriorities = skillPriorities ?: existingGoals.skillPriorities,
            targetCompletionDate = targetCompletionDate ?: existingGoals.targetCompletionDate,
            updatedAt = LocalDateTime.now()
        )
        
        return learningGoalsRepository.save(updatedGoals)
    }

    fun getNotificationSettings(userId: String): NotificationSettings {
        logger.debug("Getting notification settings for user: $userId")
        
        return notificationSettingsRepository.findByUserId(userId) ?: run {
            logger.info("Creating default notification settings for user: $userId")
            val defaultSettings = createDefaultNotificationSettings(userId)
            notificationSettingsRepository.save(defaultSettings)
        }
    }

    fun updateNotificationSettings(
        userId: String,
        dailyReminders: Boolean?,
        reminderTime: LocalTime?,
        achievementAlerts: Boolean?,
        weeklyProgress: Boolean?,
        lessonReminders: Boolean?,
        streakAlerts: Boolean?,
        emailNotifications: Boolean?,
        pushNotifications: Boolean?
    ): NotificationSettings {
        logger.debug("Updating notification settings for user: $userId")
        
        val existingSettings = getNotificationSettings(userId)
        val updatedSettings = existingSettings.copy(
            dailyReminders = dailyReminders ?: existingSettings.dailyReminders,
            reminderTime = reminderTime ?: existingSettings.reminderTime,
            achievementAlerts = achievementAlerts ?: existingSettings.achievementAlerts,
            weeklyProgress = weeklyProgress ?: existingSettings.weeklyProgress,
            lessonReminders = lessonReminders ?: existingSettings.lessonReminders,
            streakAlerts = streakAlerts ?: existingSettings.streakAlerts,
            emailNotifications = emailNotifications ?: existingSettings.emailNotifications,
            pushNotifications = pushNotifications ?: existingSettings.pushNotifications,
            updatedAt = LocalDateTime.now()
        )
        
        return notificationSettingsRepository.save(updatedSettings)
    }

    private fun createDefaultProfile(userId: String): UserProfile {
        val now = LocalDateTime.now()
        return UserProfile(
            id = null,
            userId = userId,
            displayName = "Tarifit Learner",
            preferredLanguage = "en",
            timezone = "UTC",
            profileImageUrl = null,
            bio = null,
            createdAt = now,
            updatedAt = now
        )
    }

    private fun createDefaultLearningGoals(userId: String): LearningGoals {
        val now = LocalDateTime.now()
        return LearningGoals(
            id = null,
            userId = userId,
            dailyXpTarget = 50,
            weeklyLessonsTarget = 7,
            skillPriorities = emptyList(),
            targetCompletionDate = null,
            isActive = true,
            createdAt = now,
            updatedAt = now
        )
    }

    private fun createDefaultNotificationSettings(userId: String): NotificationSettings {
        val now = LocalDateTime.now()
        return NotificationSettings(
            id = null,
            userId = userId,
            dailyReminders = true,
            reminderTime = LocalTime.of(19, 0),
            achievementAlerts = true,
            weeklyProgress = true,
            lessonReminders = true,
            streakAlerts = true,
            emailNotifications = false,
            pushNotifications = true,
            createdAt = now,
            updatedAt = now
        )
    }

    private fun validateLearningGoals(dailyXpTarget: Int?, weeklyLessonsTarget: Int?) {
        dailyXpTarget?.let { target ->
            require(target > 0 && target <= 500) { "Daily XP target must be between 1 and 500" }
        }
        
        weeklyLessonsTarget?.let { target ->
            require(target > 0 && target <= 50) { "Weekly lessons target must be between 1 and 50" }
        }
    }
}