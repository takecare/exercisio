package io.exercis.workouts.domain.model

import io.exercic.exercises.domain.Exercise
import java.time.Duration

data class WorkoutExercise(
    val exercise: Exercise,
    val duration: Duration
    // # of series? # of reps per series?
)

data class Workout(
    val name: String,
    val description: String?,
    val exercises: List<WorkoutExercise>
)
