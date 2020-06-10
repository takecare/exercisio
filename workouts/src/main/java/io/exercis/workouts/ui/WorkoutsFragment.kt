package io.exercis.workouts.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import io.exercic.base.di.GenericSavedStateViewModelFactory
import io.exercis.workouts.WorkoutsComponentProvider
import io.exercis.workouts.databinding.FragmentWorkoutsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class WorkoutsFragment : BaseFragment() {

    companion object {
        fun newInstance() = WorkoutsFragment()
    }

    private lateinit var binding: FragmentWorkoutsBinding

    @Inject
    lateinit var viewModelFactory: WorkoutsViewModelFactory

    private val viewModel: WorkoutsViewModel by viewModels {
        GenericSavedStateViewModelFactory(viewModelFactory, this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as WorkoutsComponentProvider)
            .provideWorkoutsComponent()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWorkoutsBinding.inflate(layoutInflater, container, false)

        binding.workoutsButton.setOnClickListener { emit(WorkoutsEvent.ButtonClicked) }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        emitWhenStarted(WorkoutsEvent.Displayed) // is this really needed?

        // expose flow of ui events to the view model
        viewModel.observe(events.asFlow())

        // observe the state the view model outputs
        viewModel.observeState(viewLifecycleOwner, Observer { state ->
            when {
                state.isLoading -> {
                    Toast.makeText(context, "loading", Toast.LENGTH_SHORT)
                        .show()
                }
                state.hasData -> {
                    val workouts = state?.data
                    println(workouts)
                }
                state.hasError -> {
                    Toast.makeText(context, "error", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })

        viewModel.observeEffects(viewLifecycleOwner, Observer { effect ->
            when (effect) {
                is WorkoutsEffect.Toast -> Toast.makeText(
                    context,
                    effect.message,
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        })
    }
}

fun View.onClick(channel: Channel<WorkoutsEvent>, scope: CoroutineScope) {
    scope.launch { channel.send(WorkoutsEvent.ButtonClicked) }
}

// rename to EventfulFragment?
abstract class BaseFragment : Fragment() {
    // see also https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/-conflated-broadcast-channel/
    // https://stackoverflow.com/questions/56111292/publishsubject-with-kotlin-coroutines-flow
    val events = BroadcastChannel<WorkoutsEvent>(1)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun BaseFragment.emit(event: WorkoutsEvent) = lifecycleScope.launch { events.send(event) }

@OptIn(ExperimentalCoroutinesApi::class)
fun BaseFragment.emitWhenStarted(event: WorkoutsEvent) =
    lifecycleScope.launchWhenStarted { events.send(event) }

@OptIn(ExperimentalCoroutinesApi::class)
fun BroadcastChannel<WorkoutsEvent>.emit(event: WorkoutsEvent, scope: CoroutineScope) =
    scope.launch { send(event) }
