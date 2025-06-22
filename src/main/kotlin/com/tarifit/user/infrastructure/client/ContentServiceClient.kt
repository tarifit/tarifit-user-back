package com.tarifit.user.infrastructure.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader

@FeignClient(
    name = "content-service",
    url = "\${tarifit.services.content.url}"
)
interface ContentServiceClient {
    
    @GetMapping("/api/achievements/{achievementId}")
    fun getAchievementDetails(
        @PathVariable achievementId: String,
        @RequestHeader("Authorization") authorization: String
    ): AchievementDetailsResponse
    
    @GetMapping("/api/achievements")
    fun getAllAchievements(
        @RequestHeader("Authorization") authorization: String
    ): List<AchievementDetailsResponse>
    
    @GetMapping("/api/content/dictionary/words/{wordId}")
    fun getWordDetails(
        @PathVariable wordId: String,
        @RequestHeader("Authorization") authorization: String
    ): WordDetailsResponse
}

data class AchievementDetailsResponse(
    val id: String,
    val name: String,
    val description: String,
    val type: String,
    val requirement: String,
    val xpReward: Int,
    val badgeImageUrl: String?
)

data class WordDetailsResponse(
    val id: String,
    val tarifit: String,
    val french: String,
    val arabic: String,
    val category: String,
    val difficulty: String
)