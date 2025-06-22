package com.tarifit.user.infrastructure.repository

import com.tarifit.user.infrastructure.entity.*
import org.springframework.data.mongodb.repository.MongoRepository
import java.time.LocalDate

interface LearningGoalsMongoRepository : MongoRepository<LearningGoalsEntity, String> {
    fun findByUserId(userId: String): LearningGoalsEntity?
    fun deleteByUserId(userId: String)
}

interface NotificationSettingsMongoRepository : MongoRepository<NotificationSettingsEntity, String> {
    fun findByUserId(userId: String): NotificationSettingsEntity?
    fun deleteByUserId(userId: String)
}

interface UserProgressMongoRepository : MongoRepository<UserProgressEntity, String> {
    fun findByUserId(userId: String): List<UserProgressEntity>
    fun findByUserIdAndSkillId(userId: String, skillId: String): UserProgressEntity?
    fun deleteByUserId(userId: String)
}

interface ExerciseProgressMongoRepository : MongoRepository<ExerciseProgressEntity, String> {
    fun findByUserId(userId: String): List<ExerciseProgressEntity>
    fun findByUserIdAndExerciseId(userId: String, exerciseId: String): ExerciseProgressEntity?
    fun findByUserIdAndSkillId(userId: String, skillId: String): List<ExerciseProgressEntity>
    fun deleteByUserId(userId: String)
}

interface UserStatsMongoRepository : MongoRepository<UserStatsEntity, String> {
    fun findByUserId(userId: String): UserStatsEntity?
    fun deleteByUserId(userId: String)
}

interface UserAchievementMongoRepository : MongoRepository<UserAchievementEntity, String> {
    fun findByUserId(userId: String): List<UserAchievementEntity>
    fun findByUserIdAndAchievementId(userId: String, achievementId: String): UserAchievementEntity?
    fun deleteByUserId(userId: String)
}

interface StudySessionMongoRepository : MongoRepository<StudySessionEntity, String> {
    fun findByUserId(userId: String): List<StudySessionEntity>
    fun findByUserIdAndDate(userId: String, date: LocalDate): StudySessionEntity?
    fun findByUserIdAndIsActive(userId: String, isActive: Boolean): StudySessionEntity?
    fun deleteByUserId(userId: String)
}