package com.example.gurudev

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.example.gurudev.R.id.fragment_container
import com.example.gurudev.R.id.thesis

class ThesisFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_thesis, container, false)

        val articleButton: ImageButton = view.findViewById(R.id.article)
        val lifestyleButton: ImageButton = view.findViewById(R.id.lifestyle)
        val journal1Button: ImageButton = view.findViewById(R.id.journal1)
        val journal2Button: ImageButton = view.findViewById(R.id.journal2)

        articleButton.setOnClickListener { openWebView("https://www.dsvv.ac.in/dsvv-publications/anahat/") }
        lifestyleButton.setOnClickListener { openWebView("https://sites.google.com/dsvv.ac.in/dahh-dsvv/research/shodhamala?authuser=0") }
        journal1Button.setOnClickListener { openWebView("https://dsiij.dsvv.ac.in/index.php/dsiij") }
        journal2Button.setOnClickListener { openWebView("https://ijyr.dsvv.ac.in/index.php/ijyr") }

        return view
    }

    private fun openWebView(url: String) {
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(fragment_container, WebViewFragment.newInstance(url))
            addToBackStack(null)  // Allows back navigation
            commit()
        }
    }

}
