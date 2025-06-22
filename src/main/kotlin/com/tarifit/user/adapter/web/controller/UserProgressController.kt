package com.tarifit.user.adapter.web.controller

import com.tarifit.user.adapter.dto.*
import com.tarifit.user.application.UserProgressService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users/progress")
@CrossOrigin(origins = ["*"])
class UserProgressController(
    private val userProgressService: UserProgressService
) {
    private val logger = LoggerFactory.getLogger(UserProgressController::class.java)

    @GetMapping
    fun getProgressSummary(@RequestHeader("X-User-Id") userId: String): ResponseEntity<UserProgressSummaryResponse> {
        logger.debug("Getting progress summary for user: $userId")
        
        val summary = userProgressService.getProgressSummary(userId)
        val skillResponses = summary.skillProgresses.map { progress ->
            UserProgressResponse(
                id = progress.id,
                userId = progress.userId,
                skillId = progress.skillId,
                completedExercises = progress.completedExercises,
                totalExercises = progress.totalExercises,
                skillLevel = progress.skillLevel,
                isCompleted = progress.isCompleted,
                lastExerciseDate = progress.lastExerciseDate,
                createdAt = progress.createdAt,
                updatedAt = progress.updatedAt
            )
        }
        
        val response = UserProgressSummaryResponse(
            totalSkills = summary.totalSkills,
            completedSkills = summary.completedSkills,
            totalExercises = summary.totalExercises,
            completedExercises = summary.completedExercises,
            overallProgress = summary.overallProgress,
            skillProgresses = skillResponses
        )
        
        return ResponseEntity.ok(response)
    }

    @GetMapping("/skills/{skillId}")
    fun getSkillProgress(
        @RequestHeader("X-User-Id") userId: String,
        @PathVariable skillId: String
    ): ResponseEntity<UserProgressResponse> {
        logger.debug("Getting skill progress for user: $userId, skill: $skillId")
        
        val progress = userProgressService.getSkillProgress(userId, skillId)
        val response = UserProgressResponse(
            id = progress.id,
            userId = progress.userId,
            skillId = progress.skillId,
            completedExercises = progress.completedExercises,
            totalExercises = progress.totalExercises,
            skillLevel = progress.skillLevel,
            isCompleted = progress.isCompleted,
            lastExerciseDate = progress.lastExerciseDate,
            createdAt = progress.createdAt,
            updatedAt = progress.updatedAt
        )
        
        return ResponseEntity.ok(response)
    }

    @PostMapping("/complete-exercise")
    fun completeExercise(
        @RequestHeader("X-User-Id") userId: String,
        @RequestBody request: ExerciseCompletionRequest
    ): ResponseEntity<ExerciseCompletionResponse> {
        logger.debug("Completing exercise for user: $userId, exercise: ${request.exerciseId}")
        
        val result = userProgressService.completeExercise(
            userId = userId,
            exerciseId = request.exerciseId,
            skillId = request.skillId,
            isCorrect = request.isCorrect,
            timeSpent = request.timeSpent
        )
        
        val response = ExerciseCompletionResponse(
            exerciseId = result.exerciseId,
            skillId = result.skillId,
            isCorrect = result.isCorrect,
            xpEarned = result.xpEarned,
            attempts = result.attempts,
            isFirstAttempt = result.isFirstAttempt,
            timeSpent = result.timeSpent
        )
        
        return ResponseEntity.ok(response)
    }
}