package com.tarifit.user.adapter.web.controller

import com.tarifit.user.adapter.dto.AchievementCheckResponse
import com.tarifit.user.adapter.dto.UserAchievementResponse
import com.tarifit.user.application.UserAchievementService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users/achievements")
@CrossOrigin(origins = ["*"])
class UserAchievementController(
    private val userAchievementService: UserAchievementService
) {
    private val logger = LoggerFactory.getLogger(UserAchievementController::class.java)

    @GetMapping
    fun getUserAchievements(@RequestHeader("X-User-Id") userId: String): ResponseEntity<List<UserAchievementResponse>> {
        logger.debug("Getting achievements for user: $userId")
        
        val achievements = userAchievementService.getUserAchievements(userId)
        val responses = achievements.map { achievement ->
            UserAchievementResponse(
                id = achievement.id,
                userId = achievement.userId,
                achievementId = achievement.achievementId,
                unlockedAt = achievement.unlockedAt,
                progress = achievement.progress,
                isUnlocked = achievement.isUnlocked,
                createdAt = achievement.createdAt,
                updatedAt = achievement.updatedAt
            )
        }
        
        return ResponseEntity.ok(responses)
    }

    @PostMapping("/{achievementId}/check")
    fun checkAchievement(
        @RequestHeader("X-User-Id") userId: String,
        @PathVariable achievementId: String
    ): ResponseEntity<AchievementCheckResponse> {
        logger.debug("Checking achievement $achievementId for user: $userId")
        
        val result = userAchievementService.checkAndUnlockAchievement(userId, achievementId)
        val response = AchievementCheckResponse(
            achievementId = result.achievementId,
            isUnlocked = result.isUnlocked,
            progress = result.progress,
            justUnlocked = result.justUnlocked,
            xpRewarded = result.xpRewarded
        )
        
        return ResponseEntity.ok(response)
    }
}