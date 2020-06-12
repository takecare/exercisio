package io.exercic.exercises.data

import io.exercic.exercises.data.model.ExerciseDataModel

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
