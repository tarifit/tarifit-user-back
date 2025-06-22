package com.tarifit.user.infrastructure.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.index.Indexed
import java.time.LocalDate
import java.time.LocalDateTime

@Document(collection = "study_sessions")
data class StudySessionEntity(
    @Id
    val id: String? = null,
    
    @Indexed
    val userId: String,
    
    val date: LocalDate,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime? = null,
    val xpEarned: Int,
    val exercisesCompleted: Int,
    val isActive: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)