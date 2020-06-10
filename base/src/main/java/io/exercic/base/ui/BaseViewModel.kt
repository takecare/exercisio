package io.exercic.base.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

abstract class BaseViewModel<EVENT, STATE, EFFECT>(setup: () -> Unit) : ViewModel() {

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
