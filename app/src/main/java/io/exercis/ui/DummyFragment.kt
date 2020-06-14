package io.exercis.ui

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import io.exercis.databinding.FragmentDummyBinding

const val ARG_PARAM = "param"

class DummyFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DummyFragment().apply {
                arguments = Bundle().apply { putString(ARG_PARAM, param1) }
            }
    }

    private var param: String? = null
    private lateinit var binding: FragmentDummyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { param = it.getString(ARG_PARAM) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDummyBinding.inflate(layoutInflater, container, false)

        param?.let { binding.dummyText.text = it }

        binding.dummyButton.setOnClickListener {
            findNavController().navigate(Uri.parse("exercisio://exercises"))
        }

        return binding.root;
    }
}
