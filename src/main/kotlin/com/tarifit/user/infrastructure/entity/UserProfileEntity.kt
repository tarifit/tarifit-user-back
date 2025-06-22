package com.tarifit.user.infrastructure.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.index.Indexed
import java.time.LocalDateTime

@Document(collection = "user_profiles")
data class UserProfileEntity(
    @Id
    val id: String? = null,
    
    @Indexed(unique = true)
    val userId: String,
    
    val displayName: String,
    val preferredLanguage: String,
    val timezone: String,
    val profileImageUrl: String? = null,
    val bio: String? = null,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)