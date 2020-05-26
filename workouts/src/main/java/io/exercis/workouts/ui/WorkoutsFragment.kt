package io.exercis.workouts.ui

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import io.exercis.workouts.databinding.FragmentWorkoutsBinding

class WorkoutsFragment : Fragment() {

    companion object {
        fun newInstance() = WorkoutsFragment()
    }

    private lateinit var binding: FragmentWorkoutsBinding
    private lateinit var viewModel: WorkoutsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWorkoutsBinding.inflate(layoutInflater, container, false)

        binding.workoutsButton.setOnClickListener {
            findNavController().navigate(Uri.parse("exercisio://dummy"))
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(WorkoutsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
