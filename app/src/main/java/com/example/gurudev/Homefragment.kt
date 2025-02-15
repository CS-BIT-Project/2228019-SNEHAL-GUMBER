package com.example.gurudev

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.FragmentTransaction

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Homefragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Homefragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_homefragment, container, false)

        // Find the button and set click listener
        val literatureButton: ImageButton = view.findViewById(R.id.literature)
        val thesisButton: ImageButton = view.findViewById(R.id.thesis)
        val yogaButton: ImageButton = view.findViewById(R.id.yoga)
        literatureButton.setOnClickListener {
            // Load LiteratureFragment when button is clicked
            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.container, LiteratureFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }
thesisButton.setOnClickListener {
// Load LiteratureFragment when button is clicked
    val transaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
    transaction.replace(R.id.container, ThesisFragment())
    transaction.addToBackStack(null)
    transaction.commit()}
        yogaButton.setOnClickListener {
            val transaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container, yoga())
            transaction.addToBackStack(null)
            transaction.commit()
        }
        return view
    }

}


