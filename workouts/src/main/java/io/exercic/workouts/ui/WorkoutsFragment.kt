package io.exercic.workouts.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import io.exercic.workouts.R

class WorkoutsFragment : Fragment() {

    companion object {
        fun newInstance() = WorkoutsFragment()
    }

    private lateinit var viewModel: WorkoutsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_workouts, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(WorkoutsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
