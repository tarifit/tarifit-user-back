package com.tarifit.user.infrastructure.repository

import com.tarifit.user.infrastructure.entity.UserProfileEntity
import org.springframework.data.mongodb.repository.MongoRepository

interface UserProfileMongoRepository : MongoRepository<UserProfileEntity, String> {
    fun findByUserId(userId: String): UserProfileEntity?
    fun deleteByUserId(userId: String)
}