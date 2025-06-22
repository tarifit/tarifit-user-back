package com.tarifit.user.infrastructure.repository

import com.tarifit.user.domain.entity.UserProfile
import com.tarifit.user.domain.port.UserProfileRepository
import com.tarifit.user.infrastructure.mapper.UserProfileMapper
import org.springframework.stereotype.Repository

@Repository
class UserProfileRepositoryImpl(
    private val mongoRepository: UserProfileMongoRepository
) : UserProfileRepository {
    
    override fun findByUserId(userId: String): UserProfile? {
        return mongoRepository.findByUserId(userId)?.let { UserProfileMapper.toDomain(it) }
    }
    
    override fun save(userProfile: UserProfile): UserProfile {
        val entity = UserProfileMapper.toEntity(userProfile)
        val savedEntity = mongoRepository.save(entity)
        return UserProfileMapper.toDomain(savedEntity)
    }
    
    override fun deleteByUserId(userId: String) {
        mongoRepository.deleteByUserId(userId)
    }
}