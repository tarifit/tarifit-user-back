package com.tarifit.user.application

import com.tarifit.user.domain.entity.UserAchievement
import com.tarifit.user.domain.port.UserAchievementRepository
import com.tarifit.user.domain.port.UserStatsRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class UserAchievementService(
    private val userAchievementRepository: UserAchievementRepository,
    private val userStatsRepository: UserStatsRepository,
    private val userStatsService: UserStatsService
) {
    private val logger = LoggerFactory.getLogger(UserAchievementService::class.java)

    fun getUserAchievements(userId: String): List<UserAchievement> {
        logger.debug("Getting achievements for user: $userId")
        return userAchievementRepository.findByUserId(userId)
    }

    fun checkAndUnlockAchievement(userId: String, achievementId: String): AchievementCheckResult {
        logger.debug("Checking achievement $achievementId for user: $userId")
        
        val existingAchievement = userAchievementRepository.findByUserIdAndAchievementId(userId, achievementId)
        
        if (existingAchievement?.isUnlocked == true) {
            return AchievementCheckResult(
                achievementId = achievementId,
                isUnlocked = true,
                progress = existingAchievement.progress,
                justUnlocked = false,
                xpRewarded = 0
            )
        }
        
        val progress = calculateAchievementProgress(userId, achievementId)
        val isUnlocked = progress >= 100
        val justUnlocked = isUnlocked && (existingAchievement?.isUnlocked != true)
        
        val now = LocalDateTime.now()
        val achievement = UserAchievement(
            id = existingAchievement?.id,
            userId = userId,
            achievementId = achievementId,
            unlockedAt = if (isUnlocked) now else existingAchievement?.unlockedAt,
            progress = progress,
            isUnlocked = isUnlocked,
            createdAt = existingAchievement?.createdAt ?: now,
            updatedAt = now
        )
        
        userAchievementRepository.save(achievement)
        
        var xpRewarded = 0
        if (justUnlocked) {
            xpRewarded = getAchievementXPReward(achievementId)
            userStatsService.addXP(userId, xpRewarded, "achievement_unlock")
            logger.info("Achievement $achievementId unlocked for user $userId. XP reward: $xpRewarded")
        }
        
        return AchievementCheckResult(
            achievementId = achievementId,
            isUnlocked = isUnlocked,
            progress = progress,
            justUnlocked = justUnlocked,
            xpRewarded = xpRewarded
        )
    }

    fun calculateAchievementProgress(userId: String, achievementId: String): Int {
        logger.debug("Calculating progress for achievement $achievementId, user: $userId")
        
        val userStats = userStatsRepository.findByUserId(userId) ?: return 0
        
        return when (achievementId) {
            "first_lesson" -> if (userStats.exercisesCompleted >= 1) 100 else 0
            "ten_exercises" -> minOf(100, (userStats.exercisesCompleted * 10))
            "hundred_exercises" -> minOf(100, userStats.exercisesCompleted)
            "first_skill" -> if (userStats.skillsCompleted >= 1) 100 else 0
            "five_skills" -> minOf(100, (userStats.skillsCompleted * 20))
            "streak_3" -> if (userStats.currentStreak >= 3) 100 else minOf(100, (userStats.currentStreak * 33))
            "streak_7" -> if (userStats.currentStreak >= 7) 100 else minOf(100, (userStats.currentStreak * 14))
            "streak_30" -> if (userStats.currentStreak >= 30) 100 else minOf(100, (userStats.currentStreak * 3))
            "level_5" -> if (userStats.currentLevel >= 5) 100 else minOf(100, (userStats.currentLevel * 20))
            "level_10" -> if (userStats.currentLevel >= 10) 100 else minOf(100, (userStats.currentLevel * 10))
            "xp_1000" -> minOf(100, (userStats.totalXP / 10))
            "xp_5000" -> minOf(100, (userStats.totalXP / 50))
            "xp_10000" -> minOf(100, (userStats.totalXP / 100))
            "study_time_60" -> minOf(100, (userStats.totalStudyTime / 6) * 10)
            "study_time_300" -> minOf(100, (userStats.totalStudyTime / 30) * 10)
            "perfectionist" -> calculatePerfectionistProgress(userId)
            "speed_demon" -> calculateSpeedDemonProgress(userId)
            else -> 0
        }
    }

    private fun calculatePerfectionistProgress(userId: String): Int {
        return 0
    }

    private fun calculateSpeedDemonProgress(userId: String): Int {
        return 0
    }

    private fun getAchievementXPReward(achievementId: String): Int {
        return when (achievementId) {
            "first_lesson" -> 20
            "ten_exercises" -> 50
            "hundred_exercises" -> 200
            "first_skill" -> 100
            "five_skills" -> 300
            "streak_3" -> 75
            "streak_7" -> 150
            "streak_30" -> 500
            "level_5" -> 250
            "level_10" -> 500
            "xp_1000" -> 100
            "xp_5000" -> 300
            "xp_10000" -> 600
            "study_time_60" -> 100
            "study_time_300" -> 400
            "perfectionist" -> 800
            "speed_demon" -> 600
            else -> 50
        }
    }
}

data class AchievementCheckResult(
    val achievementId: String,
    val isUnlocked: Boolean,
    val progress: Int,
    val justUnlocked: Boolean,
    val xpRewarded: Int
)