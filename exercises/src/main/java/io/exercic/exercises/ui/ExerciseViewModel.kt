package io.exercic.exercises.ui

import androidx.lifecycle.SavedStateHandle
import io.exercic.base.ui.BaseViewModel
import io.exercic.base.ui.Effect
import io.exercic.base.ui.Event
import io.exercic.base.ui.State
import io.exercic.exercises.domain.Exercise


class ExerciseViewModel constructor(
//    private val getWorkouts: GetWorkoutsUseCase,
    handle: SavedStateHandle
) : BaseViewModel<ExercisesState, ExercisesEffect, ExercisesEvent>(
    ExercisesState(isLoading = true),
    handle
) {

    override fun handleEvent(event: ExercisesEvent) {
        when (event) {
            is ExercisesEvent.Displayed -> {
                //
            }
            is ExercisesEvent.ExerciseClicked -> {
                //
            }
        }
    }

    private suspend fun loadData() {
        emit(currentState?.copy(isLoading = true))
        val exercises = emptyList<Exercise>()
        emit(
            currentState?.copy(
                isLoading = false,
                data = exercises
            )
        )
    }
}

sealed class ExercisesEvent : Event {
    object Displayed : ExercisesEvent()
    data class ExerciseClicked(val exercise: Exercise) : ExercisesEvent()
}

sealed class ExercisesEffect : Effect {
    // for toasts, navigation, etc. (pushed from view model to view)
    data class Toast(val message: String) : ExercisesEffect()
}

data class ExercisesState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val data: List<Exercise>? = null
) : State {
    val hasData: Boolean get() = data != null
}
