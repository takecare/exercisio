package io.exercis.workouts.data

import io.exercis.exercises.data.DummyData.Companion.exercises
import io.exercis.workouts.data.model.WorkoutDataModel
import io.exercis.workouts.data.model.WorkoutExerciseDataModel

class DummyData {
    companion object {
        val workoutExercises = exercises.mapIndexed { i, e -> WorkoutExerciseDataModel(e, i + 1) }
        val workouts = (1 until workoutExercises.size).map {
            WorkoutDataModel(
                "workout $it",
                "workout desc $it",
                listOf(workoutExercises[it - 1], workoutExercises[it])
            )
        }
    }
}
