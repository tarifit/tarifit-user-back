package com.tarifit.user.adapter.web.controller

import com.tarifit.user.adapter.dto.*
import com.tarifit.user.application.UserStatsService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users/stats")
@CrossOrigin(origins = ["*"])
class UserStatsController(
    private val userStatsService: UserStatsService
) {
    private val logger = LoggerFactory.getLogger(UserStatsController::class.java)

    @GetMapping
    fun getUserStats(@RequestHeader("X-User-Id") userId: String): ResponseEntity<UserStatsResponse> {
        logger.debug("Getting stats for user: $userId")
        
        val stats = userStatsService.getUserStats(userId)
        val response = UserStatsResponse(
            id = stats.id,
            userId = stats.userId,
            totalXP = stats.totalXP,
            currentLevel = stats.currentLevel,
            currentStreak = stats.currentStreak,
            longestStreak = stats.longestStreak,
            lastStudyDate = stats.lastStudyDate,
            totalStudyTime = stats.totalStudyTime,
            exercisesCompleted = stats.exercisesCompleted,
            skillsCompleted = stats.skillsCompleted,
            createdAt = stats.createdAt,
            updatedAt = stats.updatedAt
        )
        
        return ResponseEntity.ok(response)
    }

    @GetMapping("/dashboard")
    fun getDashboardStats(@RequestHeader("X-User-Id") userId: String): ResponseEntity<DashboardStatsResponse> {
        logger.debug("Getting dashboard stats for user: $userId")
        
        val dashboardStats = userStatsService.getDashboardStats(userId)
        val response = DashboardStatsResponse(
            totalXP = dashboardStats.totalXP,
            currentLevel = dashboardStats.currentLevel,
            currentStreak = dashboardStats.currentStreak,
            longestStreak = dashboardStats.longestStreak,
            exercisesCompleted = dashboardStats.exercisesCompleted,
            skillsCompleted = dashboardStats.skillsCompleted,
            totalStudyTime = dashboardStats.totalStudyTime,
            weeklyXP = dashboardStats.weeklyXP,
            averageSessionTime = dashboardStats.averageSessionTime,
            lastStudyDate = dashboardStats.lastStudyDate
        )
        
        return ResponseEntity.ok(response)
    }

    @PostMapping("/add-xp")
    fun addXP(
        @RequestHeader("X-User-Id") userId: String,
        @RequestBody request: AddXPRequest
    ): ResponseEntity<AddXPResponse> {
        logger.debug("Adding ${request.amount} XP to user: $userId")
        
        val originalStats = userStatsService.getUserStats(userId)
        val updatedStats = userStatsService.addXP(userId, request.amount, request.source)
        
        val levelUp = updatedStats.currentLevel > originalStats.currentLevel
        
        val response = AddXPResponse(
            totalXP = updatedStats.totalXP,
            currentLevel = updatedStats.currentLevel,
            xpAdded = request.amount,
            levelUp = levelUp
        )
        
        return ResponseEntity.ok(response)
    }

    @PostMapping("/start-session")
    fun startStudySession(@RequestHeader("X-User-Id") userId: String): ResponseEntity<StudySessionResponse> {
        logger.debug("Starting study session for user: $userId")
        
        val session = userStatsService.startStudySession(userId)
        val response = StudySessionResponse(
            id = session.id,
            userId = session.userId,
            date = session.date,
            startTime = session.startTime,
            endTime = session.endTime,
            xpEarned = session.xpEarned,
            exercisesCompleted = session.exercisesCompleted,
            isActive = session.isActive,
            createdAt = session.createdAt,
            updatedAt = session.updatedAt
        )
        
        return ResponseEntity.ok(response)
    }

    @PostMapping("/end-session")
    fun endStudySession(
        @RequestHeader("X-User-Id") userId: String,
        @RequestBody request: EndSessionRequest
    ): ResponseEntity<StudySessionResponse> {
        logger.debug("Ending study session for user: $userId")
        
        val session = userStatsService.endStudySession(userId, request.xpEarned, request.exercisesCompleted)
        val response = StudySessionResponse(
            id = session.id,
            userId = session.userId,
            date = session.date,
            startTime = session.startTime,
            endTime = session.endTime,
            xpEarned = session.xpEarned,
            exercisesCompleted = session.exercisesCompleted,
            isActive = session.isActive,
            createdAt = session.createdAt,
            updatedAt = session.updatedAt
        )
        
        return ResponseEntity.ok(response)
    }
}