package io.exercic.workouts.ui

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
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
        val view = inflater.inflate(R.layout.fragment_workouts, container, false)

        view.findViewById<Button>(R.id.workouts_button).setOnClickListener {
            findNavController().navigate(Uri.parse("exercisio://dummy"))
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(WorkoutsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
