package com.tarifit.user.domain.entity

import java.time.LocalDateTime

data class UserProfile(
    val id: String?,
    val userId: String,
    val displayName: String,
    val preferredLanguage: String,
    val timezone: String,
    val profileImageUrl: String?,
    val bio: String?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)