package com.tarifit.user.application

import com.tarifit.user.domain.entity.StudySession
import com.tarifit.user.domain.entity.UserStats
import com.tarifit.user.domain.port.StudySessionRepository
import com.tarifit.user.domain.port.UserStatsRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.math.sqrt

@Service
class UserStatsService(
    private val userStatsRepository: UserStatsRepository,
    private val studySessionRepository: StudySessionRepository
) {
    private val logger = LoggerFactory.getLogger(UserStatsService::class.java)

    fun getUserStats(userId: String): UserStats {
        logger.debug("Getting stats for user: $userId")
        
        return userStatsRepository.findByUserId(userId) ?: run {
            logger.info("Creating default stats for user: $userId")
            val defaultStats = createDefaultStats(userId)
            userStatsRepository.save(defaultStats)
        }
    }

    fun addXP(userId: String, amount: Int, source: String): UserStats {
        logger.debug("Adding $amount XP to user $userId from source: $source")
        
        require(amount > 0) { "XP amount must be positive" }
        require(amount <= 100) { "XP amount cannot exceed 100 per operation" }
        
        val currentStats = getUserStats(userId)
        val newTotalXP = currentStats.totalXP + amount
        val newLevel = calculateCurrentLevel(newTotalXP)
        
        val updatedStats = currentStats.copy(
            totalXP = newTotalXP,
            currentLevel = newLevel,
            updatedAt = LocalDateTime.now()
        )
        
        logger.info("User $userId gained $amount XP. Total: $newTotalXP, Level: $newLevel")
        return userStatsRepository.save(updatedStats)
    }

    fun calculateCurrentLevel(totalXP: Int): Int {
        return when {
            totalXP < 0 -> 1
            totalXP < 100 -> 1
            else -> (sqrt(totalXP.toDouble() / 100.0) + 1).toInt()
        }
    }

    fun updateStreakStatus(userId: String): UserStats {
        logger.debug("Updating streak status for user: $userId")
        
        val currentStats = getUserStats(userId)
        val today = LocalDate.now()
        val yesterday = today.minusDays(1)
        
        val newStreak = when (currentStats.lastStudyDate) {
            today -> currentStats.currentStreak
            yesterday -> currentStats.currentStreak + 1
            null -> 1
            else -> 1
        }
        
        val newLongestStreak = maxOf(currentStats.longestStreak, newStreak)
        
        val updatedStats = currentStats.copy(
            currentStreak = newStreak,
            longestStreak = newLongestStreak,
            lastStudyDate = today,
            updatedAt = LocalDateTime.now()
        )
        
        logger.info("User $userId streak updated. Current: $newStreak, Longest: $newLongestStreak")
        return userStatsRepository.save(updatedStats)
    }

    fun startStudySession(userId: String): StudySession {
        logger.debug("Starting study session for user: $userId")
        
        val activeSession = studySessionRepository.findActiveByUserId(userId)
        if (activeSession != null) {
            logger.warn("User $userId already has an active session")
            return activeSession
        }
        
        val now = LocalDateTime.now()
        val newSession = StudySession(
            id = null,
            userId = userId,
            date = now.toLocalDate(),
            startTime = now,
            endTime = null,
            xpEarned = 0,
            exercisesCompleted = 0,
            isActive = true,
            createdAt = now,
            updatedAt = now
        )
        
        return studySessionRepository.save(newSession)
    }

    fun endStudySession(userId: String, xpEarned: Int, exercisesCompleted: Int): StudySession {
        logger.debug("Ending study session for user: $userId")
        
        val activeSession = studySessionRepository.findActiveByUserId(userId)
            ?: throw IllegalStateException("No active study session found for user $userId")
        
        val now = LocalDateTime.now()
        val sessionDuration = java.time.Duration.between(activeSession.startTime, now).toMinutes()
        
        val updatedSession = activeSession.copy(
            endTime = now,
            xpEarned = xpEarned,
            exercisesCompleted = exercisesCompleted,
            isActive = false,
            updatedAt = now
        )
        
        val updatedStats = getUserStats(userId)
        val newStats = updatedStats.copy(
            totalStudyTime = updatedStats.totalStudyTime + sessionDuration,
            exercisesCompleted = updatedStats.exercisesCompleted + exercisesCompleted,
            updatedAt = now
        )
        userStatsRepository.save(newStats)
        
        updateStreakStatus(userId)
        
        logger.info("Study session ended for user $userId. Duration: ${sessionDuration}min, XP: $xpEarned, Exercises: $exercisesCompleted")
        return studySessionRepository.save(updatedSession)
    }

    fun getDashboardStats(userId: String): DashboardStats {
        logger.debug("Getting dashboard stats for user: $userId")
        
        val userStats = getUserStats(userId)
        val sessions = studySessionRepository.findByUserId(userId)
        val weeklyXP = calculateWeeklyXP(sessions)
        val averageSessionTime = calculateAverageSessionTime(sessions)
        
        return DashboardStats(
            totalXP = userStats.totalXP,
            currentLevel = userStats.currentLevel,
            currentStreak = userStats.currentStreak,
            longestStreak = userStats.longestStreak,
            exercisesCompleted = userStats.exercisesCompleted,
            skillsCompleted = userStats.skillsCompleted,
            totalStudyTime = userStats.totalStudyTime,
            weeklyXP = weeklyXP,
            averageSessionTime = averageSessionTime,
            lastStudyDate = userStats.lastStudyDate
        )
    }

    private fun createDefaultStats(userId: String): UserStats {
        val now = LocalDateTime.now()
        return UserStats(
            id = null,
            userId = userId,
            totalXP = 0,
            currentLevel = 1,
            currentStreak = 0,
            longestStreak = 0,
            lastStudyDate = null,
            totalStudyTime = 0,
            exercisesCompleted = 0,
            skillsCompleted = 0,
            createdAt = now,
            updatedAt = now
        )
    }

    private fun calculateWeeklyXP(sessions: List<StudySession>): Int {
        val weekAgo = LocalDate.now().minusDays(7)
        return sessions
            .filter { it.date.isAfter(weekAgo) || it.date.isEqual(weekAgo) }
            .sumOf { it.xpEarned }
    }

    private fun calculateAverageSessionTime(sessions: List<StudySession>): Long {
        val completedSessions = sessions.filter { !it.isActive && it.endTime != null }
        if (completedSessions.isEmpty()) return 0
        
        val totalTime = completedSessions.sumOf { session ->
            session.endTime?.let { endTime ->
                java.time.Duration.between(session.startTime, endTime).toMinutes()
            } ?: 0
        }
        
        return totalTime / completedSessions.size
    }
}

data class DashboardStats(
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