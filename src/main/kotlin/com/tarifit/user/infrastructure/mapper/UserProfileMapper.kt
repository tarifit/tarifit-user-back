package com.tarifit.user.infrastructure.mapper

import com.tarifit.user.domain.entity.UserProfile
import com.tarifit.user.infrastructure.entity.UserProfileEntity

object UserProfileMapper {
    
    fun toDomain(entity: UserProfileEntity): UserProfile {
        return UserProfile(
            id = entity.id,
            userId = entity.userId,
            displayName = entity.displayName,
            preferredLanguage = entity.preferredLanguage,
            timezone = entity.timezone,
            profileImageUrl = entity.profileImageUrl,
            bio = entity.bio,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt
        )
    }
    
    fun toEntity(domain: UserProfile): UserProfileEntity {
        return UserProfileEntity(
            id = domain.id,
            userId = domain.userId,
            displayName = domain.displayName,
            preferredLanguage = domain.preferredLanguage,
            timezone = domain.timezone,
            profileImageUrl = domain.profileImageUrl,
            bio = domain.bio,
            createdAt = domain.createdAt,
            updatedAt = domain.updatedAt
        )
    }
}