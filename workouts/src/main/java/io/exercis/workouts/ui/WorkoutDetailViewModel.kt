package io.exercis.workouts.ui

import androidx.lifecycle.SavedStateHandle
import io.exercis.base.ui.BaseViewModel
import io.exercis.base.ui.Effect
import io.exercis.base.ui.Event
import io.exercis.base.ui.State
import io.exercis.exercises.domain.Exercise
import io.exercis.workouts.domain.model.Workout

class WorkoutDetailViewModel(
    handle: SavedStateHandle
) : BaseViewModel<WorkoutDetailsState, WorkoutDetailsEffect, WorkoutDetailsEvent>(
    WorkoutDetailsState(isLoading = true),
    handle
) {
    override fun handleEvent(event: WorkoutDetailsEvent) {
        // TODO: handleEvent not implemented
    }
}

sealed class WorkoutDetailsEvent : Event {
    object Displayed : WorkoutDetailsEvent()
    data class ExerciseClicked(val exercise: Exercise) : WorkoutDetailsEvent()
}

sealed class WorkoutDetailsEffect : Effect {
    // for toasts, navigation, etc. (pushed from view model to view)

    data class NavigateToExercise(val exercise: Exercise) : WorkoutDetailsEffect()
}

data class WorkoutDetailsState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val data: List<Workout>? = null
) : State {
    val hasData: Boolean get() = data != null
}
