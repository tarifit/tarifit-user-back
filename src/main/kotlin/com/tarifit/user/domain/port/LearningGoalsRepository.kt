package com.tarifit.user.domain.port

import com.tarifit.user.domain.entity.LearningGoals

interface LearningGoalsRepository {
    fun findByUserId(userId: String): LearningGoals?
    fun save(learningGoals: LearningGoals): LearningGoals
    fun deleteByUserId(userId: String)
}