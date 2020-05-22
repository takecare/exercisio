package io.exercis.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import io.exercis.R

class AnotherFragment : Fragment() {
    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: AnotherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_another, container, false)

        view.findViewById<Button>(R.id.anotherButton).setOnClickListener {
            val bundle = bundleOf(ARG_PARAM to "argvalue")
            Navigation.findNavController(it).navigate(
                R.id.action_another_to_dummy,
                bundle
            )
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AnotherViewModel::class.java)
        // TODO: Use the ViewModel
    }
}
