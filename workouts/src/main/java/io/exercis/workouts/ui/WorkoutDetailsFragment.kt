package io.exercis.workouts.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import io.exercis.base.di.GenericSavedStateViewModelFactory
import io.exercis.base.ui.BaseFragment
import io.exercis.workouts.WorkoutsComponentProvider
import io.exercis.workouts.databinding.FragmentWorkoutDetailsBinding
import javax.inject.Inject

class WorkoutDetailsFragment : BaseFragment<WorkoutDetailsEvent>() {

    private lateinit var binding: FragmentWorkoutDetailsBinding

    @Inject
    lateinit var viewModelFactory: WorkoutDetailsViewModelFactory

    private val workoutName: String?
        get() = arguments?.getString("name")

    private val viewModel: WorkoutDetailViewModel by viewModels {
        GenericSavedStateViewModelFactory(viewModelFactory, this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as WorkoutsComponentProvider)
            .provideWorkoutsComponent()
            .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle -> bundle.keySet().forEach { println("$it: ${bundle[it]}") } }

//        val action: String? = intent?.action
//        val data: Uri? = intent?.data
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWorkoutDetailsBinding.inflate(layoutInflater, container, false)

        binding.workoutName.text = workoutName

        return binding.root
    }
}
