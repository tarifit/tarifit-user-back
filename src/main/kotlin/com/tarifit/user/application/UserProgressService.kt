package com.tarifit.user.application

import com.tarifit.user.domain.entity.ExerciseProgress
import com.tarifit.user.domain.entity.UserProgress
import com.tarifit.user.domain.port.ExerciseProgressRepository
import com.tarifit.user.domain.port.UserProgressRepository
import com.tarifit.user.domain.port.UserStatsRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class UserProgressService(
    private val userProgressRepository: UserProgressRepository,
    private val exerciseProgressRepository: ExerciseProgressRepository,
    private val userStatsRepository: UserStatsRepository,
    private val userStatsService: UserStatsService
) {
    private val logger = LoggerFactory.getLogger(UserProgressService::class.java)

    fun getProgressSummary(userId: String): UserProgressSummary {
        logger.debug("Getting progress summary for user: $userId")
        
        val skillProgresses = userProgressRepository.findByUserId(userId)
        val totalSkills = skillProgresses.size
        val completedSkills = skillProgresses.count { it.isCompleted }
        val totalExercises = skillProgresses.sumOf { it.totalExercises }
        val completedExercises = skillProgresses.sumOf { it.completedExercises }
        
        val overallProgress = if (totalExercises > 0) {
            (completedExercises.toDouble() / totalExercises.toDouble() * 100).toInt()
        } else 0
        
        return UserProgressSummary(
            totalSkills = totalSkills,
            completedSkills = completedSkills,
            totalExercises = totalExercises,
            completedExercises = completedExercises,
            overallProgress = overallProgress,
            skillProgresses = skillProgresses
        )
    }

    fun getSkillProgress(userId: String, skillId: String): UserProgress {
        logger.debug("Getting skill progress for user: $userId, skill: $skillId")
        
        return userProgressRepository.findByUserIdAndSkillId(userId, skillId) ?: run {
            logger.info("Creating default progress for user: $userId, skill: $skillId")
            val defaultProgress = createDefaultSkillProgress(userId, skillId)
            userProgressRepository.save(defaultProgress)
        }
    }

    fun completeExercise(userId: String, exerciseId: String, skillId: String, isCorrect: Boolean, timeSpent: Long): ExerciseCompletionResult {
        logger.debug("Completing exercise for user: $userId, exercise: $exerciseId, skill: $skillId")
        
        require(timeSpent >= 0) { "Time spent cannot be negative" }
        
        val existingProgress = exerciseProgressRepository.findByUserIdAndExerciseId(userId, exerciseId)
        val isFirstAttempt = existingProgress == null
        val attempts = (existingProgress?.attempts ?: 0) + 1
        
        val xpEarned = calculateExerciseXP("standard", isCorrect, isFirstAttempt, timeSpent)
        
        val now = LocalDateTime.now()
        val exerciseProgress = ExerciseProgress(
            id = existingProgress?.id,
            userId = userId,
            exerciseId = exerciseId,
            skillId = skillId,
            completed = isCorrect,
            attempts = attempts,
            isCorrect = isCorrect,
            timeSpent = timeSpent,
            xpEarned = if (isCorrect) xpEarned else 0,
            completedAt = if (isCorrect) now else existingProgress?.completedAt,
            createdAt = existingProgress?.createdAt ?: now,
            updatedAt = now
        )
        
        exerciseProgressRepository.save(exerciseProgress)
        
        if (isCorrect && isFirstAttempt) {
            updateSkillCompletion(userId, skillId)
        }
        
        if (isCorrect) {
            userStatsService.addXP(userId, xpEarned, "exercise_completion")
        }
        
        logger.info("Exercise completed for user $userId. Correct: $isCorrect, XP: $xpEarned, Attempts: $attempts")
        
        return ExerciseCompletionResult(
            exerciseId = exerciseId,
            skillId = skillId,
            isCorrect = isCorrect,
            xpEarned = if (isCorrect) xpEarned else 0,
            attempts = attempts,
            isFirstAttempt = isFirstAttempt,
            timeSpent = timeSpent
        )
    }

    fun calculateExerciseXP(exerciseType: String, isCorrect: Boolean, isFirstTry: Boolean, timeSpent: Long): Int {
        if (!isCorrect) return 0
        
        val baseXP = when (exerciseType) {
            "vocabulary" -> 10
            "grammar" -> 15
            "listening" -> 20
            "speaking" -> 25
            else -> 10
        }
        
        val firstTryBonus = if (isFirstTry) (baseXP * 0.5).toInt() else 0
        
        val timeBonus = when {
            timeSpent < 30 -> (baseXP * 0.3).toInt()
            timeSpent < 60 -> (baseXP * 0.2).toInt()
            timeSpent < 120 -> (baseXP * 0.1).toInt()
            else -> 0
        }
        
        return baseXP + firstTryBonus + timeBonus
    }

    fun updateSkillCompletion(userId: String, skillId: String) {
        logger.debug("Updating skill completion for user: $userId, skill: $skillId")
        
        val skillProgress = getSkillProgress(userId, skillId)
        val exerciseProgresses = exerciseProgressRepository.findByUserIdAndSkillId(userId, skillId)
        val completedExercises = exerciseProgresses.count { it.completed }
        
        val isSkillCompleted = skillProgress.totalExercises > 0 && completedExercises >= skillProgress.totalExercises
        
        val updatedProgress = skillProgress.copy(
            completedExercises = completedExercises,
            isCompleted = isSkillCompleted,
            lastExerciseDate = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
        
        userProgressRepository.save(updatedProgress)
        
        if (isSkillCompleted && !skillProgress.isCompleted) {
            val bonusXP = 50
            userStatsService.addXP(userId, bonusXP, "skill_completion")
            
            val userStats = userStatsRepository.findByUserId(userId)
            userStats?.let { stats ->
                val updatedStats = stats.copy(
                    skillsCompleted = stats.skillsCompleted + 1,
                    updatedAt = LocalDateTime.now()
                )
                userStatsRepository.save(updatedStats)
            }
            
            logger.info("Skill $skillId completed for user $userId. Bonus XP: $bonusXP")
        }
    }

    fun checkSkillUnlock(userId: String, skillId: String): Boolean {
        logger.debug("Checking skill unlock for user: $userId, skill: $skillId")
        
        val prerequisiteSkills = getSkillPrerequisites(skillId)
        if (prerequisiteSkills.isEmpty()) {
            return true
        }
        
        val userProgresses = userProgressRepository.findByUserId(userId)
        val completedSkillIds = userProgresses.filter { it.isCompleted }.map { it.skillId }
        
        val allPrerequisitesMet = prerequisiteSkills.all { prerequisite ->
            completedSkillIds.contains(prerequisite)
        }
        
        logger.debug("Skill $skillId unlock status for user $userId: $allPrerequisitesMet")
        return allPrerequisitesMet
    }

    private fun createDefaultSkillProgress(userId: String, skillId: String): UserProgress {
        val now = LocalDateTime.now()
        val totalExercises = getSkillTotalExercises(skillId)
        
        return UserProgress(
            id = null,
            userId = userId,
            skillId = skillId,
            completedExercises = 0,
            totalExercises = totalExercises,
            skillLevel = 1,
            isCompleted = false,
            lastExerciseDate = null,
            createdAt = now,
            updatedAt = now
        )
    }

    private fun getSkillPrerequisites(skillId: String): List<String> {
        return when (skillId) {
            "basic_greetings" -> emptyList()
            "basic_numbers" -> listOf("basic_greetings")
            "basic_family" -> listOf("basic_greetings", "basic_numbers")
            "intermediate_conversation" -> listOf("basic_greetings", "basic_numbers", "basic_family")
            else -> emptyList()
        }
    }

    private fun getSkillTotalExercises(skillId: String): Int {
        return when (skillId) {
            "basic_greetings" -> 20
            "basic_numbers" -> 15
            "basic_family" -> 25
            "intermediate_conversation" -> 30
            else -> 20
        }
    }
}

data class UserProgressSummary(
    val totalSkills: Int,
    val completedSkills: Int,
    val totalExercises: Int,
    val completedExercises: Int,
    val overallProgress: Int,
    val skillProgresses: List<UserProgress>
)

data class ExerciseCompletionResult(
    val exerciseId: String,
    val skillId: String,
    val isCorrect: Boolean,
    val xpEarned: Int,
    val attempts: Int,
    val isFirstAttempt: Boolean,
    val timeSpent: Long
)