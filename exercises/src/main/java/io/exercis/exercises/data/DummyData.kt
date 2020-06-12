package io.exercis.exercises.data

import io.exercis.exercises.data.model.ExerciseDataModel

class DummyData {
    companion object {
        val exercises = (1..10).map {
            ExerciseDataModel(
                "exercise_data_model_$it",
                "exercise $it",
                "description $it"
            )
        }
    }
}
