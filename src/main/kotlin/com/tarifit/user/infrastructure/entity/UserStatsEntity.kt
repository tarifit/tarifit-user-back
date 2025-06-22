package com.tarifit.user.infrastructure.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.index.Indexed
import java.time.LocalDate
import java.time.LocalDateTime

@Document(collection = "user_stats")
data class UserStatsEntity(
    @Id
    val id: String? = null,
    
    @Indexed(unique = true)
    val userId: String,
    
    val totalXP: Int,
    val currentLevel: Int,
    val currentStreak: Int,
    val longestStreak: Int,
    val lastStudyDate: LocalDate? = null,
    val totalStudyTime: Long,
    val exercisesCompleted: Int,
    val skillsCompleted: Int,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)