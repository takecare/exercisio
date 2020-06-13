package io.exercis.exercises.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.exercis.base.ui.BaseFragment
import io.exercis.exercises.databinding.FragmentExercisesBinding
import io.exercis.exercises.ExercisesComponentProvider

// this fragment should be able to support:
// 1. empty list of exercises
// 2. list of exercises
// 3. add new exercise (to current workout)

class ExercisesFragment : BaseFragment<ExercisesEvent>() {

    private lateinit var binding: FragmentExercisesBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as ExercisesComponentProvider)
            .provideExercisesComponent()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExercisesBinding.inflate(layoutInflater, container, false)

        // TODO setup emissions

        return binding.root
    }
}
