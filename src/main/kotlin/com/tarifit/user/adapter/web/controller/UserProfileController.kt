package com.tarifit.user.adapter.web.controller

import com.tarifit.user.adapter.dto.*
import com.tarifit.user.application.UserProfileService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = ["*"])
class UserProfileController(
    private val userProfileService: UserProfileService
) {
    private val logger = LoggerFactory.getLogger(UserProfileController::class.java)

    @GetMapping("/profile")
    fun getUserProfile(@RequestHeader("X-User-Id") userId: String): ResponseEntity<UserProfileResponse> {
        logger.debug("Getting profile for user: $userId")
        
        val userProfile = userProfileService.getOrCreateProfile(userId)
        val response = UserProfileResponse(
            id = userProfile.id,
            userId = userProfile.userId,
            displayName = userProfile.displayName,
            preferredLanguage = userProfile.preferredLanguage,
            timezone = userProfile.timezone,
            profileImageUrl = userProfile.profileImageUrl,
            bio = userProfile.bio,
            createdAt = userProfile.createdAt,
            updatedAt = userProfile.updatedAt
        )
        
        return ResponseEntity.ok(response)
    }

    @PutMapping("/profile")
    fun updateUserProfile(
        @RequestHeader("X-User-Id") userId: String,
        @RequestBody request: UserProfileUpdateRequest
    ): ResponseEntity<UserProfileResponse> {
        logger.debug("Updating profile for user: $userId")
        
        val updatedProfile = userProfileService.updateProfile(
            userId = userId,
            displayName = request.displayName,
            preferredLanguage = request.preferredLanguage,
            timezone = request.timezone,
            profileImageUrl = request.profileImageUrl,
            bio = request.bio
        )
        
        val response = UserProfileResponse(
            id = updatedProfile.id,
            userId = updatedProfile.userId,
            displayName = updatedProfile.displayName,
            preferredLanguage = updatedProfile.preferredLanguage,
            timezone = updatedProfile.timezone,
            profileImageUrl = updatedProfile.profileImageUrl,
            bio = updatedProfile.bio,
            createdAt = updatedProfile.createdAt,
            updatedAt = updatedProfile.updatedAt
        )
        
        return ResponseEntity.ok(response)
    }

    @GetMapping("/learning-goals")
    fun getLearningGoals(@RequestHeader("X-User-Id") userId: String): ResponseEntity<LearningGoalsResponse> {
        logger.debug("Getting learning goals for user: $userId")
        
        val learningGoals = userProfileService.getLearningGoals(userId)
        val response = LearningGoalsResponse(
            id = learningGoals.id,
            userId = learningGoals.userId,
            dailyXpTarget = learningGoals.dailyXpTarget,
            weeklyLessonsTarget = learningGoals.weeklyLessonsTarget,
            skillPriorities = learningGoals.skillPriorities,
            targetCompletionDate = learningGoals.targetCompletionDate,
            isActive = learningGoals.isActive,
            createdAt = learningGoals.createdAt,
            updatedAt = learningGoals.updatedAt
        )
        
        return ResponseEntity.ok(response)
    }

    @PutMapping("/learning-goals")
    fun updateLearningGoals(
        @RequestHeader("X-User-Id") userId: String,
        @RequestBody request: LearningGoalsUpdateRequest
    ): ResponseEntity<LearningGoalsResponse> {
        logger.debug("Updating learning goals for user: $userId")
        
        val updatedGoals = userProfileService.updateLearningGoals(
            userId = userId,
            dailyXpTarget = request.dailyXpTarget,
            weeklyLessonsTarget = request.weeklyLessonsTarget,
            skillPriorities = request.skillPriorities,
            targetCompletionDate = request.targetCompletionDate
        )
        
        val response = LearningGoalsResponse(
            id = updatedGoals.id,
            userId = updatedGoals.userId,
            dailyXpTarget = updatedGoals.dailyXpTarget,
            weeklyLessonsTarget = updatedGoals.weeklyLessonsTarget,
            skillPriorities = updatedGoals.skillPriorities,
            targetCompletionDate = updatedGoals.targetCompletionDate,
            isActive = updatedGoals.isActive,
            createdAt = updatedGoals.createdAt,
            updatedAt = updatedGoals.updatedAt
        )
        
        return ResponseEntity.ok(response)
    }

    @GetMapping("/notification-settings")
    fun getNotificationSettings(@RequestHeader("X-User-Id") userId: String): ResponseEntity<NotificationSettingsResponse> {
        logger.debug("Getting notification settings for user: $userId")
        
        val settings = userProfileService.getNotificationSettings(userId)
        val response = NotificationSettingsResponse(
            id = settings.id,
            userId = settings.userId,
            dailyReminders = settings.dailyReminders,
            reminderTime = settings.reminderTime,
            achievementAlerts = settings.achievementAlerts,
            weeklyProgress = settings.weeklyProgress,
            lessonReminders = settings.lessonReminders,
            streakAlerts = settings.streakAlerts,
            emailNotifications = settings.emailNotifications,
            pushNotifications = settings.pushNotifications,
            createdAt = settings.createdAt,
            updatedAt = settings.updatedAt
        )
        
        return ResponseEntity.ok(response)
    }

    @PutMapping("/notification-settings")
    fun updateNotificationSettings(
        @RequestHeader("X-User-Id") userId: String,
        @RequestBody request: NotificationSettingsUpdateRequest
    ): ResponseEntity<NotificationSettingsResponse> {
        logger.debug("Updating notification settings for user: $userId")
        
        val updatedSettings = userProfileService.updateNotificationSettings(
            userId = userId,
            dailyReminders = request.dailyReminders,
            reminderTime = request.reminderTime,
            achievementAlerts = request.achievementAlerts,
            weeklyProgress = request.weeklyProgress,
            lessonReminders = request.lessonReminders,
            streakAlerts = request.streakAlerts,
            emailNotifications = request.emailNotifications,
            pushNotifications = request.pushNotifications
        )
        
        val response = NotificationSettingsResponse(
            id = updatedSettings.id,
            userId = updatedSettings.userId,
            dailyReminders = updatedSettings.dailyReminders,
            reminderTime = updatedSettings.reminderTime,
            achievementAlerts = updatedSettings.achievementAlerts,
            weeklyProgress = updatedSettings.weeklyProgress,
            lessonReminders = updatedSettings.lessonReminders,
            streakAlerts = updatedSettings.streakAlerts,
            emailNotifications = updatedSettings.emailNotifications,
            pushNotifications = updatedSettings.pushNotifications,
            createdAt = updatedSettings.createdAt,
            updatedAt = updatedSettings.updatedAt
        )
        
        return ResponseEntity.ok(response)
    }
}