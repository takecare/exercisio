package io.exercis.workouts.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.exercis.workouts.domain.GetWorkoutsUseCase
import io.exercis.workouts.domain.model.Workout
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

// https://medium.com/androiddevelopers/viewmodels-and-livedata-patterns-antipatterns-21efaef74a54
// https://stackoverflow.com/questions/47515997/observing-livedata-from-viewmodel

class WorkoutsViewModel() : ViewModel() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> get() = _state

    private val _effect = MutableLiveData<Effect>()
    val effect: LiveData<Effect> get() = _effect

    fun observe(events: Flow<Event>) {
        viewModelScope.launch {
            events.collect { event ->
                when (event) {
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

}

sealed class Event {
    object Nothing : Event() // can be dropped
    object ButtonClicked : Event()
}

// TODO merge Effect and State? e.g. Pair<State, Effect> and effect has a 'consumed' bool

sealed class Effect {
    // for toasts, navigation, etc. (pushed from view model to view)

    data class Toast(val message: String) : Effect()
}

sealed class State {
    object Loading : State()
    data class Error(val message: String) : State()
    data class Data<T>(val data: T) : State()
}

fun <T> MutableLiveData<T>.asLiveData() = this as LiveData<T>
