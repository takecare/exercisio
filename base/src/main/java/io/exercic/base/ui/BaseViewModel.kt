package io.exercic.base.ui

import androidx.lifecycle.*
import io.exercic.base.di.ViewModelAssistedFactory
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

// TODO merge Effect and State? e.g. Pair<State, Effect> and effect has a 'consumed' bool
interface State
interface Effect
interface Event

abstract class BaseViewModel<S : State, E : Effect, Ev : Event>(
    private val initialState: S,
    private val handle: SavedStateHandle
) : ViewModel() {

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

    fun observe(events: BroadcastChannel<Ev>) {
        observe(events.asFlow())
    }

    fun observe(events: Flow<Ev>) { // rename to bind()? connect()?
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
