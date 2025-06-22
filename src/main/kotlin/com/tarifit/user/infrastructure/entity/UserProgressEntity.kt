package com.tarifit.user.infrastructure.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.index.CompoundIndex
import java.time.LocalDateTime

@Document(collection = "user_progress")
@CompoundIndex(def = "{'userId': 1, 'skillId': 1}", unique = true)
data class UserProgressEntity(
    @Id
    val id: String? = null,
    
    @Indexed
    val userId: String,
    
    val skillId: String,
    val completedExercises: Int,
    val totalExercises: Int,
    val skillLevel: Int,
    val isCompleted: Boolean,
    val lastExerciseDate: LocalDateTime? = null,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)