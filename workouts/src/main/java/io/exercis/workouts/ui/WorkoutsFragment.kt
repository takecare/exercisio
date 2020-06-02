package io.exercis.workouts.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import io.exercis.workouts.WorkoutsComponentProvider
import io.exercis.workouts.databinding.FragmentWorkoutsBinding
import io.exercis.workouts.domain.GetWorkoutsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class WorkoutsViewModelFactory(
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        return if (modelClass.isAssignableFrom(WorkoutsViewModel::class.java)) {
            WorkoutsViewModel()
        } else {
            throw IllegalStateException("")
        } as T
    }
}

class WorkoutsFragment : BaseFragment() {

    companion object {
        fun newInstance() = WorkoutsFragment()
    }

    private lateinit var binding: FragmentWorkoutsBinding
    @Inject
    lateinit var useCase: GetWorkoutsUseCase

    private val viewModel: WorkoutsViewModel by viewModels { WorkoutsViewModelFactory(this) }

    private val _oldflow: MutableStateFlow<Event> = MutableStateFlow<Event>(Event.Nothing)
    private val oldflow get() = _oldflow

    // see also https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/-conflated-broadcast-channel/
    // https://stackoverflow.com/questions/56111292/publishsubject-with-kotlin-coroutines-flow
    // private val events = BroadcastChannel<Event>(1)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWorkoutsBinding.inflate(layoutInflater, container, false)

        binding.workoutsButton.setOnClickListener { emit(Event.ButtonClicked) }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity?.application as WorkoutsComponentProvider)
            .provideWorkoutsComponent()
            .inject(this)


        // expose flow of ui events to the view model
        viewModel.observe(events.asFlow())

        // observe the state the view model outputs
        viewModel.state.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                // TODO
            }
        })
    }
}

fun View.onClick(channel: Channel<Event>, scope: CoroutineScope) {
    scope.launch { channel.send(Event.ButtonClicked) }
}

abstract class BaseFragment : Fragment() {
    val events = BroadcastChannel<Event>(1)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //
    }
}

@ExperimentalCoroutinesApi
fun BaseFragment.emit(event: Event) = lifecycleScope.launch { events.send(event) }

@ExperimentalCoroutinesApi
fun BroadcastChannel<Event>.emit(event: Event, scope: CoroutineScope) =
    scope.launch { send(event) }
