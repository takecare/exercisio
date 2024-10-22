package io.exercis.workouts.ui

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.exercis.base.di.GenericSavedStateViewModelFactory
import io.exercis.base.ui.BaseFragment
import io.exercis.workouts.WorkoutsComponentProvider
import io.exercis.workouts.databinding.FragmentWorkoutsBinding
import io.exercis.workouts.databinding.RowWorkoutBinding
import io.exercis.workouts.domain.model.Workout
import javax.inject.Inject

// this fragment should be able to support:
// 1. empty list of workouts
// 2. list of workouts
// 3. create new workout
// 4. navigate to workout (list of exercises)
// 5. change workout details (eg. name) (still not sure about this one)

class WorkoutsFragment : BaseFragment<WorkoutsEvent>() {

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

        binding.workoutsButton.clicksEmit(WorkoutsEvent.Displayed)

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
                    val workouts = state?.data ?: emptyList()
                    with(binding.recyclerView) {
                        layoutManager = LinearLayoutManager(context)
                        adapter = WorkoutsRecyclerAdapter(workouts) {
                            emit(WorkoutsEvent.WorkoutClicked(it))
                        }
                    }
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
                is WorkoutsEffect.NavigateToWorkout -> {
                    val workout = effect.workout
//                    val request = NavDeepLinkRequest.Builder
//                        .fromUri()
                    findNavController().navigate(Uri.parse("exercisio://workout/${workout.name}"))
                }
            }
        })
    }
}

// https://developer.android.com/guide/topics/ui/layout/recyclerview

class WorkoutsViewHolder(
    private val binding: RowWorkoutBinding,
    private val listener: (Workout) -> Unit,
    view: View
) : RecyclerView.ViewHolder(view) {

    fun bind(workout: Workout) {
        binding.mainText.text = workout.name
        binding.secondaryText.text = workout.description
        itemView.setOnClickListener { listener(workout) }
    }
}

class WorkoutsRecyclerAdapter(private val listener: (Workout) -> Unit) :
    RecyclerView.Adapter<WorkoutsViewHolder>() {

    var data: List<Workout> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    constructor(data: List<Workout>, listener: (Workout) -> Unit) : this(listener) {
        this.data = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutsViewHolder {
        val binding = RowWorkoutBinding.inflate(LayoutInflater.from(parent.context))
        return WorkoutsViewHolder(binding, listener, binding.root)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: WorkoutsViewHolder, position: Int) {
        val workout = data[position]
        holder.bind(workout)
    }
}
