package com.tarifit.user.infrastructure.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.index.Indexed
import java.time.LocalDateTime

@Document(collection = "learning_goals")
data class LearningGoalsEntity(
    @Id
    val id: String? = null,
    
    @Indexed(unique = true)
    val userId: String,
    
    val dailyXpTarget: Int,
    val weeklyLessonsTarget: Int,
    val skillPriorities: List<String>,
    val targetCompletionDate: LocalDateTime? = null,
    val isActive: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)