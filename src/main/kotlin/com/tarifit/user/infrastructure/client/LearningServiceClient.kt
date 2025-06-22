package com.tarifit.user.infrastructure.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader

@FeignClient(
    name = "learning-service",
    url = "\${tarifit.services.learning.url}"
)
interface LearningServiceClient {
    
    @GetMapping("/api/skills/{skillId}")
    fun getSkillDetails(
        @PathVariable skillId: String,
        @RequestHeader("Authorization") authorization: String
    ): SkillDetailsResponse
    
    @GetMapping("/api/skills/{skillId}/exercises")
    fun getSkillExercises(
        @PathVariable skillId: String,
        @RequestHeader("Authorization") authorization: String
    ): List<ExerciseDetailsResponse>
    
    @GetMapping("/api/exercises/{exerciseId}")
    fun getExerciseDetails(
        @PathVariable exerciseId: String,
        @RequestHeader("Authorization") authorization: String
    ): ExerciseDetailsResponse
}

data class SkillDetailsResponse(
    val id: String,
    val name: String,
    val description: String,
    val level: String,
    val totalExercises: Int,
    val prerequisiteSkills: List<String>
)

data class ExerciseDetailsResponse(
    val id: String,
    val skillId: String,
    val type: String,
    val title: String,
    val description: String,
    val difficulty: String
)