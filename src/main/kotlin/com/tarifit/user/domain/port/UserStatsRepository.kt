package com.tarifit.user.domain.port

import com.tarifit.user.domain.entity.UserStats

interface UserStatsRepository {
    fun findByUserId(userId: String): UserStats?
    fun save(userStats: UserStats): UserStats
    fun deleteByUserId(userId: String)
}