package io.exercis.base.ui

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.launch

// rename to EventfulFragment?
abstract class BaseFragment<E : Event> : Fragment() {
    val events = BroadcastChannel<E>(1)

    fun emit(event: E) = lifecycleScope.launch { events.send(event) }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun emitWhenStarted(event: E) =
        lifecycleScope.launchWhenStarted { events.send(event) }

    fun View.clicksEmit(event: E) {
        setOnClickListener { emit(event) }
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
fun <E : Event> BroadcastChannel<E>.emit(event: E, scope: CoroutineScope) =
    scope.launch { send(event) }

