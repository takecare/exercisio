package io.exercis.workouts.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import io.exercis.base.ui.BaseViewModel
import io.exercis.base.ui.Effect
import io.exercis.base.ui.Event
import io.exercis.base.ui.State
import io.exercis.workouts.domain.GetWorkoutsUseCase
import io.exercis.workouts.domain.model.Workout
import io.exercis.workouts.domain.model.Workouts
import kotlinx.coroutines.launch

// https://medium.com/androiddevelopers/viewmodels-and-livedata-patterns-antipatterns-21efaef74a54
// https://stackoverflow.com/questions/47515997/observing-livedata-from-viewmodel

class WorkoutsViewModel constructor(
    private val getWorkouts: GetWorkoutsUseCase,
    handle: SavedStateHandle
) : BaseViewModel<WorkoutsState, WorkoutsEffect, WorkoutsEvent>(
    WorkoutsState(isLoading = true),
    handle
) {

    override fun handleEvent(event: WorkoutsEvent) {
        when (event) {
            is WorkoutsEvent.WorkoutClicked -> {
                emit(WorkoutsEffect.NavigateToWorkout(event.workout))
            }
            is WorkoutsEvent.Displayed -> viewModelScope.launch {
                loadData()
            }
            else -> {
                // unexpected
            }
        }
    }

    private suspend fun loadData() {
        emit(currentState?.copy(isLoading = true))
        val workouts = getWorkouts.execute()
        emit(
            currentState?.copy(
                isLoading = false,
                data = workouts
            )
        )
    }
}

sealed class WorkoutsEvent : Event {
    object Displayed : WorkoutsEvent()
    data class WorkoutClicked(val workout: Workout) : WorkoutsEvent()
}

sealed class WorkoutsEffect : Effect {
    // for toasts, navigation, etc. (pushed from view model to view)

    data class NavigateToWorkout(val workout: Workout) : WorkoutsEffect()

    data class Toast(val message: String) : WorkoutsEffect()
}

data class WorkoutsState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val data: Workouts? = null
) : State {
    val hasData: Boolean get() = data != null
}
