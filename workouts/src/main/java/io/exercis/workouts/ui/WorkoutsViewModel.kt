package io.exercis.workouts.ui

import androidx.lifecycle.*
import io.exercis.workouts.domain.GetWorkoutsUseCase
import io.exercis.workouts.domain.model.Workouts
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

// https://medium.com/androiddevelopers/viewmodels-and-livedata-patterns-antipatterns-21efaef74a54
// https://stackoverflow.com/questions/47515997/observing-livedata-from-viewmodel

interface State
interface Effect
interface Event

abstract class BaseViewModel<S : State, E : Effect, Ev : Event>(
    private val initialState: S,
    private val handle: SavedStateHandle
) :
    ViewModel() {

    private val _state = MutableLiveData<S>().apply {
        postValue(initialState)
    }
    val state: LiveData<S> get() = _state

    protected val currentState: S? get() = _state.value

    fun observeState(owner: LifecycleOwner, observer: Observer<S>) {
        _state.observe(owner, observer)
    }

    // https://github.com/android/architecture-samples/blob/dev-todo-mvvm-live/todoapp/app/src/main/java/com/example/android/architecture/blueprints/todoapp/SingleLiveEvent.java
    private val _effects = MutableLiveData<E>()
    val effects: LiveData<E> get() = _effects

    fun observeEffects(owner: LifecycleOwner, observer: Observer<E>) {
        _effects.observe(owner, observer)
    }

    fun observe(events: Flow<Ev>) {
        viewModelScope.launch {
            events.collect { handleEvent(it) }
        }
    }

    fun emit(state: S?) {
        state?.let { _state.postValue(it) }
    }

    fun emit(effect: E?) {
        effect?.let { _effects.postValue(it) }
    }

    abstract fun handleEvent(event: Ev)
}

class WorkoutsViewModel constructor(
    private val getWorkouts: GetWorkoutsUseCase,
    handle: SavedStateHandle
) : BaseViewModel<WorkoutsState, WorkoutsEffect, WorkoutsEvent>(
    WorkoutsState(isLoading = true),
    handle
) {

    override fun handleEvent(event: WorkoutsEvent) {
        when (event) {
            is WorkoutsEvent.Displayed -> viewModelScope.launch {
                loadData()
            }
            is WorkoutsEvent.ButtonClicked -> {
                emit(WorkoutsEffect.Toast("button clicked"))
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
    object ButtonClicked : WorkoutsEvent()
}

// TODO merge Effect and State? e.g. Pair<State, Effect> and effect has a 'consumed' bool
sealed class WorkoutsEffect : Effect {
    // for toasts, navigation, etc. (pushed from view model to view)
    data class Toast(val message: String) : WorkoutsEffect()
}

data class WorkoutsState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val data: Workouts? = null
) : State {
    val hasData: Boolean get() = data != null
}

fun <T> MutableLiveData<T>.asLiveData() = this as LiveData<T>
