package io.exercis.exercises.domain

import kotlinx.coroutines.flow.Flow

interface ExercisesRepository {
    suspend fun getExercises(): Flow<Exercise>
    suspend fun addExercise(workout: Exercise)
}
