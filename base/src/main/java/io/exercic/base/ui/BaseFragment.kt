package io.exercic.base.ui

import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.launch

// generics for event or marker interface?
interface Event

// rename to EventfulFragment?
abstract class BaseFragment : Fragment() {
    val events = BroadcastChannel<Event>(1)
}

//@OptIn(ExperimentalCoroutinesApi::class)
//fun BaseFragment.emit(event: Event) = lifecycleScope.launch { events.send(event) }

@OptIn(ExperimentalCoroutinesApi::class)
fun BroadcastChannel<Event>.emit(event: Event, scope: CoroutineScope) =
    scope.launch { send(event) }
