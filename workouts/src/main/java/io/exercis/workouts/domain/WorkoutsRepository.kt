package io.exercis.workouts.domain

import io.exercis.workouts.domain.model.Workout
import kotlinx.coroutines.flow.Flow

interface WorkoutsRepository {
    suspend fun getWorkouts(): Flow<Workout>
    suspend fun addWorkout(workout: Workout)
}
