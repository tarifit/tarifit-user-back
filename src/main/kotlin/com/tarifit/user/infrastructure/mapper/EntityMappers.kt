package com.tarifit.user.infrastructure.mapper

import com.tarifit.user.domain.entity.*
import com.tarifit.user.infrastructure.entity.*

object LearningGoalsMapper {
    fun toDomain(entity: LearningGoalsEntity): LearningGoals {
        return LearningGoals(
            id = entity.id,
            userId = entity.userId,
            dailyXpTarget = entity.dailyXpTarget,
            weeklyLessonsTarget = entity.weeklyLessonsTarget,
            skillPriorities = entity.skillPriorities,
            targetCompletionDate = entity.targetCompletionDate,
            isActive = entity.isActive,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt
        )
    }
    
    fun toEntity(domain: LearningGoals): LearningGoalsEntity {
        return LearningGoalsEntity(
            id = domain.id,
            userId = domain.userId,
            dailyXpTarget = domain.dailyXpTarget,
            weeklyLessonsTarget = domain.weeklyLessonsTarget,
            skillPriorities = domain.skillPriorities,
            targetCompletionDate = domain.targetCompletionDate,
            isActive = domain.isActive,
            createdAt = domain.createdAt,
            updatedAt = domain.updatedAt
        )
    }
}

object NotificationSettingsMapper {
    fun toDomain(entity: NotificationSettingsEntity): NotificationSettings {
        return NotificationSettings(
            id = entity.id,
            userId = entity.userId,
            dailyReminders = entity.dailyReminders,
            reminderTime = entity.reminderTime,
            achievementAlerts = entity.achievementAlerts,
            weeklyProgress = entity.weeklyProgress,
            lessonReminders = entity.lessonReminders,
            streakAlerts = entity.streakAlerts,
            emailNotifications = entity.emailNotifications,
            pushNotifications = entity.pushNotifications,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt
        )
    }
    
    fun toEntity(domain: NotificationSettings): NotificationSettingsEntity {
        return NotificationSettingsEntity(
            id = domain.id,
            userId = domain.userId,
            dailyReminders = domain.dailyReminders,
            reminderTime = domain.reminderTime,
            achievementAlerts = domain.achievementAlerts,
            weeklyProgress = domain.weeklyProgress,
            lessonReminders = domain.lessonReminders,
            streakAlerts = domain.streakAlerts,
            emailNotifications = domain.emailNotifications,
            pushNotifications = domain.pushNotifications,
            createdAt = domain.createdAt,
            updatedAt = domain.updatedAt
        )
    }
}

object UserProgressMapper {
    fun toDomain(entity: UserProgressEntity): UserProgress {
        return UserProgress(
            id = entity.id,
            userId = entity.userId,
            skillId = entity.skillId,
            completedExercises = entity.completedExercises,
            totalExercises = entity.totalExercises,
            skillLevel = entity.skillLevel,
            isCompleted = entity.isCompleted,
            lastExerciseDate = entity.lastExerciseDate,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt
        )
    }
    
    fun toEntity(domain: UserProgress): UserProgressEntity {
        return UserProgressEntity(
            id = domain.id,
            userId = domain.userId,
            skillId = domain.skillId,
            completedExercises = domain.completedExercises,
            totalExercises = domain.totalExercises,
            skillLevel = domain.skillLevel,
            isCompleted = domain.isCompleted,
            lastExerciseDate = domain.lastExerciseDate,
            createdAt = domain.createdAt,
            updatedAt = domain.updatedAt
        )
    }
}

object ExerciseProgressMapper {
    fun toDomain(entity: ExerciseProgressEntity): ExerciseProgress {
        return ExerciseProgress(
            id = entity.id,
            userId = entity.userId,
            exerciseId = entity.exerciseId,
            skillId = entity.skillId,
            completed = entity.completed,
            attempts = entity.attempts,
            isCorrect = entity.isCorrect,
            timeSpent = entity.timeSpent,
            xpEarned = entity.xpEarned,
            completedAt = entity.completedAt,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt
        )
    }
    
    fun toEntity(domain: ExerciseProgress): ExerciseProgressEntity {
        return ExerciseProgressEntity(
            id = domain.id,
            userId = domain.userId,
            exerciseId = domain.exerciseId,
            skillId = domain.skillId,
            completed = domain.completed,
            attempts = domain.attempts,
            isCorrect = domain.isCorrect,
            timeSpent = domain.timeSpent,
            xpEarned = domain.xpEarned,
            completedAt = domain.completedAt,
            createdAt = domain.createdAt,
            updatedAt = domain.updatedAt
        )
    }
}

object UserStatsMapper {
    fun toDomain(entity: UserStatsEntity): UserStats {
        return UserStats(
            id = entity.id,
            userId = entity.userId,
            totalXP = entity.totalXP,
            currentLevel = entity.currentLevel,
            currentStreak = entity.currentStreak,
            longestStreak = entity.longestStreak,
            lastStudyDate = entity.lastStudyDate,
            totalStudyTime = entity.totalStudyTime,
            exercisesCompleted = entity.exercisesCompleted,
            skillsCompleted = entity.skillsCompleted,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt
        )
    }
    
    fun toEntity(domain: UserStats): UserStatsEntity {
        return UserStatsEntity(
            id = domain.id,
            userId = domain.userId,
            totalXP = domain.totalXP,
            currentLevel = domain.currentLevel,
            currentStreak = domain.currentStreak,
            longestStreak = domain.longestStreak,
            lastStudyDate = domain.lastStudyDate,
            totalStudyTime = domain.totalStudyTime,
            exercisesCompleted = domain.exercisesCompleted,
            skillsCompleted = domain.skillsCompleted,
            createdAt = domain.createdAt,
            updatedAt = domain.updatedAt
        )
    }
}

object UserAchievementMapper {
    fun toDomain(entity: UserAchievementEntity): UserAchievement {
        return UserAchievement(
            id = entity.id,
            userId = entity.userId,
            achievementId = entity.achievementId,
            unlockedAt = entity.unlockedAt,
            progress = entity.progress,
            isUnlocked = entity.isUnlocked,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt
        )
    }
    
    fun toEntity(domain: UserAchievement): UserAchievementEntity {
        return UserAchievementEntity(
            id = domain.id,
            userId = domain.userId,
            achievementId = domain.achievementId,
            unlockedAt = domain.unlockedAt,
            progress = domain.progress,
            isUnlocked = domain.isUnlocked,
            createdAt = domain.createdAt,
            updatedAt = domain.updatedAt
        )
    }
}

object StudySessionMapper {
    fun toDomain(entity: StudySessionEntity): StudySession {
        return StudySession(
            id = entity.id,
            userId = entity.userId,
            date = entity.date,
            startTime = entity.startTime,
            endTime = entity.endTime,
            xpEarned = entity.xpEarned,
            exercisesCompleted = entity.exercisesCompleted,
            isActive = entity.isActive,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt
        )
    }
    
    fun toEntity(domain: StudySession): StudySessionEntity {
        return StudySessionEntity(
            id = domain.id,
            userId = domain.userId,
            date = domain.date,
            startTime = domain.startTime,
            endTime = domain.endTime,
            xpEarned = domain.xpEarned,
            exercisesCompleted = domain.exercisesCompleted,
            isActive = domain.isActive,
            createdAt = domain.createdAt,
            updatedAt = domain.updatedAt
        )
    }
}