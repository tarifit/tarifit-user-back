package com.tarifit.user.domain.port

import com.tarifit.user.domain.entity.UserProfile

interface UserProfileRepository {
    fun findByUserId(userId: String): UserProfile?
    fun save(userProfile: UserProfile): UserProfile
    fun deleteByUserId(userId: String)
}