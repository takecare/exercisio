package io.exercic.base.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

// generics for event or marker interface?
// interface Event

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

sealed class Event {
    object ButtonClicked
}

sealed class Effect {
    data class Message(val content: String)
}

data class State(
    val isError: Boolean,
    val content: String
)

class MyViewModel : BaseViewModel<Event, State, Effect>() {

}

abstract class BaseFragment<Event> : Fragment() {
    private val events = BroadcastChannel<Event>(1)

    abstract val viewModel: ViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // expose flow of ui events to the view model
        viewModel.observe(events.asFlow())

        // observe the state the view model outputs
        viewModel.state.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                // TODO
            }
        })

        viewModel.effects.observe(viewLifecycleOwner, Observer { effect ->
            when (effect) {
                //
            }
        })
    }
}
