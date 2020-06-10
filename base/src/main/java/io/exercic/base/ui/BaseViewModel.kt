package io.exercic.base.ui

import androidx.lifecycle.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

// TODO merge Effect and State? e.g. Pair<State, Effect> and effect has a 'consumed' bool
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

//

abstract class _BaseViewModel<EVENT, STATE, EFFECT>(setup: () -> Unit) : ViewModel() {

    private val _state = MutableLiveData<STATE>()
    val state: LiveData<STATE> get() = _state

    // https://github.com/android/architecture-samples/blob/dev-todo-mvvm-live/todoapp/app/src/main/java/com/example/android/architecture/blueprints/todoapp/SingleLiveEvent.java
    private val _effects = MutableLiveData<EFFECT>()
    val effects: LiveData<EFFECT> get() = _effects

    private val ops: MutableMap<EVENT, (EVENT) -> Any?> = mutableMapOf()

    init {
        setup()
    }

    fun observe(events: Flow<EVENT>) {
//        viewModelScope.launch { events.collect { event -> handleEvent(event) } }
        viewModelScope.launch { events.collect { event -> matchOp(event) } }
    }

    fun on(event: EVENT, op: (EVENT) -> Any?) {
        ops[event] = op
    }

    fun Any?.produce(f: () -> Any?) {

    }

    private fun matchOp(event: EVENT) {
        ops[event]?.let { it(event) }
    }

    // on EVENT do OPERATION
    // produce EFFECT ?
    // reduce STATE

//    abstract fun handleEvent(event: EVENT)

    fun effect(effect: EFFECT) {
        _effects.postValue(effect)
    }
}
