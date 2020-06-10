package io.exercis.workouts.ui

import androidx.lifecycle.*
import io.exercis.workouts.domain.GetWorkoutsUseCase
import io.exercis.workouts.domain.model.Workouts
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

// https://medium.com/androiddevelopers/viewmodels-and-livedata-patterns-antipatterns-21efaef74a54
// https://stackoverflow.com/questions/47515997/observing-livedata-from-viewmodel

class WorkoutsViewModel constructor(
    private val getWorkouts: GetWorkoutsUseCase,
    private val handle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableLiveData<WorkoutsState>()
    val state: LiveData<WorkoutsState> get() = _state

    // https://github.com/android/architecture-samples/blob/dev-todo-mvvm-live/todoapp/app/src/main/java/com/example/android/architecture/blueprints/todoapp/SingleLiveEvent.java
    private val _effect = MutableLiveData<Effect>()
    val effects: LiveData<Effect> get() = _effect

    fun observe(events: Flow<Event>) {
        viewModelScope.launch {
            events.collect { event ->
                when (event) {
                    is Event.Displayed -> loadData()
                    is Event.ButtonClicked -> {
                        _effect.postValue(Effect.Toast("button clicked"))
                    }
                    else -> {
                        // unexpected
                    }
                }
            }
        }
    }

    private suspend fun loadData() {
        _state.postValue(state.value?.copy(isLoading = true))
        val workouts = getWorkouts.execute()
        _state.postValue(
            _state.value?.copy(
                isLoading = false,
                data = workouts
            )
        )
    }
}

sealed class Event {
    object Nothing : Event() // can be dropped
    object Displayed : Event()
    object ButtonClicked : Event()
}

// TODO merge Effect and State? e.g. Pair<State, Effect> and effect has a 'consumed' bool
sealed class Effect {
    // for toasts, navigation, etc. (pushed from view model to view)
    data class Toast(val message: String) : Effect()
}

interface State

data class WorkoutsState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val data: Workouts?
) : State {
    val hasData: Boolean get() = data != null
}

fun <T> MutableLiveData<T>.asLiveData() = this as LiveData<T>
