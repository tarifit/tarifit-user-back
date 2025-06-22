package com.tarifit.user.infrastructure.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.index.CompoundIndex
import java.time.LocalDateTime

@Document(collection = "exercise_progress")
@CompoundIndex(def = "{'userId': 1, 'exerciseId': 1}", unique = true)
data class ExerciseProgressEntity(
    @Id
    val id: String? = null,
    
    @Indexed
    val userId: String,
    
    val exerciseId: String,
    val skillId: String,
    val completed: Boolean,
    val attempts: Int,
    val isCorrect: Boolean,
    val timeSpent: Long,
    val xpEarned: Int,
    val completedAt: LocalDateTime? = null,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)