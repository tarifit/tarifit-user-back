package com.tarifit.user.domain.port

import com.tarifit.user.domain.entity.StudySession
import java.time.LocalDate

interface StudySessionRepository {
    fun findByUserId(userId: String): List<StudySession>
    fun findByUserIdAndDate(userId: String, date: LocalDate): StudySession?
    fun findActiveByUserId(userId: String): StudySession?
    fun save(studySession: StudySession): StudySession
    fun deleteByUserId(userId: String)
}