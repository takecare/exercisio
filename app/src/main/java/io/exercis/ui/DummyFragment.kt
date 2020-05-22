package io.exercis.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import io.exercis.R

const val ARG_PARAM = "param"

/**
 * A simple [Fragment] subclass.
 * Use the [DummyFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DummyFragment : Fragment() {
    private var param: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param = it.getString(ARG_PARAM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dummy, container, false)

        param?.let { view.findViewById<TextView>(R.id.dummyText).text = it }

        view.findViewById<Button>(R.id.dummyButton).setOnClickListener {
            //
        }


        return view;
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DummyFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM, param1)
                }
            }
    }
}
