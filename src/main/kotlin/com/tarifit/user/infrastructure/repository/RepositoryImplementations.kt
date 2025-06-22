package com.tarifit.user.infrastructure.repository

import com.tarifit.user.domain.entity.*
import com.tarifit.user.domain.port.*
import com.tarifit.user.infrastructure.mapper.*
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class LearningGoalsRepositoryImpl(
    private val mongoRepository: LearningGoalsMongoRepository
) : LearningGoalsRepository {
    
    override fun findByUserId(userId: String): LearningGoals? {
        return mongoRepository.findByUserId(userId)?.let { LearningGoalsMapper.toDomain(it) }
    }
    
    override fun save(learningGoals: LearningGoals): LearningGoals {
        val entity = LearningGoalsMapper.toEntity(learningGoals)
        val savedEntity = mongoRepository.save(entity)
        return LearningGoalsMapper.toDomain(savedEntity)
    }
    
    override fun deleteByUserId(userId: String) {
        mongoRepository.deleteByUserId(userId)
    }
}

@Repository
class NotificationSettingsRepositoryImpl(
    private val mongoRepository: NotificationSettingsMongoRepository
) : NotificationSettingsRepository {
    
    override fun findByUserId(userId: String): NotificationSettings? {
        return mongoRepository.findByUserId(userId)?.let { NotificationSettingsMapper.toDomain(it) }
    }
    
    override fun save(notificationSettings: NotificationSettings): NotificationSettings {
        val entity = NotificationSettingsMapper.toEntity(notificationSettings)
        val savedEntity = mongoRepository.save(entity)
        return NotificationSettingsMapper.toDomain(savedEntity)
    }
    
    override fun deleteByUserId(userId: String) {
        mongoRepository.deleteByUserId(userId)
    }
}

@Repository
class UserProgressRepositoryImpl(
    private val mongoRepository: UserProgressMongoRepository
) : UserProgressRepository {
    
    override fun findByUserId(userId: String): List<UserProgress> {
        return mongoRepository.findByUserId(userId).map { UserProgressMapper.toDomain(it) }
    }
    
    override fun findByUserIdAndSkillId(userId: String, skillId: String): UserProgress? {
        return mongoRepository.findByUserIdAndSkillId(userId, skillId)?.let { UserProgressMapper.toDomain(it) }
    }
    
    override fun save(userProgress: UserProgress): UserProgress {
        val entity = UserProgressMapper.toEntity(userProgress)
        val savedEntity = mongoRepository.save(entity)
        return UserProgressMapper.toDomain(savedEntity)
    }
    
    override fun deleteByUserId(userId: String) {
        mongoRepository.deleteByUserId(userId)
    }
}

@Repository
class ExerciseProgressRepositoryImpl(
    private val mongoRepository: ExerciseProgressMongoRepository
) : ExerciseProgressRepository {
    
    override fun findByUserId(userId: String): List<ExerciseProgress> {
        return mongoRepository.findByUserId(userId).map { ExerciseProgressMapper.toDomain(it) }
    }
    
    override fun findByUserIdAndExerciseId(userId: String, exerciseId: String): ExerciseProgress? {
        return mongoRepository.findByUserIdAndExerciseId(userId, exerciseId)?.let { ExerciseProgressMapper.toDomain(it) }
    }
    
    override fun findByUserIdAndSkillId(userId: String, skillId: String): List<ExerciseProgress> {
        return mongoRepository.findByUserIdAndSkillId(userId, skillId).map { ExerciseProgressMapper.toDomain(it) }
    }
    
    override fun save(exerciseProgress: ExerciseProgress): ExerciseProgress {
        val entity = ExerciseProgressMapper.toEntity(exerciseProgress)
        val savedEntity = mongoRepository.save(entity)
        return ExerciseProgressMapper.toDomain(savedEntity)
    }
    
    override fun deleteByUserId(userId: String) {
        mongoRepository.deleteByUserId(userId)
    }
}

@Repository
class UserStatsRepositoryImpl(
    private val mongoRepository: UserStatsMongoRepository
) : UserStatsRepository {
    
    override fun findByUserId(userId: String): UserStats? {
        return mongoRepository.findByUserId(userId)?.let { UserStatsMapper.toDomain(it) }
    }
    
    override fun save(userStats: UserStats): UserStats {
        val entity = UserStatsMapper.toEntity(userStats)
        val savedEntity = mongoRepository.save(entity)
        return UserStatsMapper.toDomain(savedEntity)
    }
    
    override fun deleteByUserId(userId: String) {
        mongoRepository.deleteByUserId(userId)
    }
}

@Repository
class UserAchievementRepositoryImpl(
    private val mongoRepository: UserAchievementMongoRepository
) : UserAchievementRepository {
    
    override fun findByUserId(userId: String): List<UserAchievement> {
        return mongoRepository.findByUserId(userId).map { UserAchievementMapper.toDomain(it) }
    }
    
    override fun findByUserIdAndAchievementId(userId: String, achievementId: String): UserAchievement? {
        return mongoRepository.findByUserIdAndAchievementId(userId, achievementId)?.let { UserAchievementMapper.toDomain(it) }
    }
    
    override fun save(userAchievement: UserAchievement): UserAchievement {
        val entity = UserAchievementMapper.toEntity(userAchievement)
        val savedEntity = mongoRepository.save(entity)
        return UserAchievementMapper.toDomain(savedEntity)
    }
    
    override fun deleteByUserId(userId: String) {
        mongoRepository.deleteByUserId(userId)
    }
}

@Repository
class StudySessionRepositoryImpl(
    private val mongoRepository: StudySessionMongoRepository
) : StudySessionRepository {
    
    override fun findByUserId(userId: String): List<StudySession> {
        return mongoRepository.findByUserId(userId).map { StudySessionMapper.toDomain(it) }
    }
    
    override fun findByUserIdAndDate(userId: String, date: LocalDate): StudySession? {
        return mongoRepository.findByUserIdAndDate(userId, date)?.let { StudySessionMapper.toDomain(it) }
    }
    
    override fun findActiveByUserId(userId: String): StudySession? {
        return mongoRepository.findByUserIdAndIsActive(userId, true)?.let { StudySessionMapper.toDomain(it) }
    }
    
    override fun save(studySession: StudySession): StudySession {
        val entity = StudySessionMapper.toEntity(studySession)
        val savedEntity = mongoRepository.save(entity)
        return StudySessionMapper.toDomain(savedEntity)
    }
    
    override fun deleteByUserId(userId: String) {
        mongoRepository.deleteByUserId(userId)
    }
}