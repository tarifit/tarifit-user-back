package com.tarifit.user.domain.port

import com.tarifit.user.domain.entity.UserAchievement

interface UserAchievementRepository {
    fun findByUserId(userId: String): List<UserAchievement>
    fun findByUserIdAndAchievementId(userId: String, achievementId: String): UserAchievement?
    fun save(userAchievement: UserAchievement): UserAchievement
    fun deleteByUserId(userId: String)
}