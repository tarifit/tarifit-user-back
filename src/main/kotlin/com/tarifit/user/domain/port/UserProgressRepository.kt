package com.tarifit.user.domain.port

import com.tarifit.user.domain.entity.UserProgress

interface UserProgressRepository {
    fun findByUserId(userId: String): List<UserProgress>
    fun findByUserIdAndSkillId(userId: String, skillId: String): UserProgress?
    fun save(userProgress: UserProgress): UserProgress
    fun deleteByUserId(userId: String)
}