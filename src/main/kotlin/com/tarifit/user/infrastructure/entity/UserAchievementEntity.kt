package com.tarifit.user.infrastructure.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.index.CompoundIndex
import java.time.LocalDateTime

@Document(collection = "user_achievements")
@CompoundIndex(def = "{'userId': 1, 'achievementId': 1}", unique = true)
data class UserAchievementEntity(
    @Id
    val id: String? = null,
    
    @Indexed
    val userId: String,
    
    val achievementId: String,
    val unlockedAt: LocalDateTime? = null,
    val progress: Int,
    val isUnlocked: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)