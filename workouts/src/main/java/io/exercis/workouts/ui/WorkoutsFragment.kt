package io.exercis.workouts.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import io.exercic.base.di.GenericSavedStateViewModelFactory
import io.exercic.base.ui.BaseFragment
import io.exercis.workouts.WorkoutsComponentProvider
import io.exercis.workouts.databinding.FragmentWorkoutsBinding
import javax.inject.Inject

class WorkoutsFragment : BaseFragment<WorkoutsEvent>() {

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

        binding.workoutsButton.clicksEmit(WorkoutsEvent.ButtonClicked)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        emitWhenStarted(WorkoutsEvent.Displayed) // is this really needed?

        // expose flow of ui events to the view model
        viewModel.observe(events)

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
