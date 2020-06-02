package io.exercis.workouts.data.model

import io.exercic.exercises.data.ExerciseDataModel

data class WorkoutExerciseDataModel(
    val exercise: ExerciseDataModel,
    val duration: Int // mins
)

data class WorkoutDataModel(
    val name: String,
    val description: String? = null,
    val exercises: List<WorkoutExerciseDataModel>
)
