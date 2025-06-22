package com.tarifit.user.domain.port

import com.tarifit.user.domain.entity.ExerciseProgress

interface ExerciseProgressRepository {
    fun findByUserId(userId: String): List<ExerciseProgress>
    fun findByUserIdAndExerciseId(userId: String, exerciseId: String): ExerciseProgress?
    fun findByUserIdAndSkillId(userId: String, skillId: String): List<ExerciseProgress>
    fun save(exerciseProgress: ExerciseProgress): ExerciseProgress
    fun deleteByUserId(userId: String)
}