package com.example.gurudev

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class Yoga : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.yoga, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up button click listeners
        view.findViewById<Button>(R.id.pragya_yog).setOnClickListener {
            // Navigate to YogaView
            val yogaViewFragment = YogaView()
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, yogaViewFragment)
                .addToBackStack(null)
                .commit()
        }
        view.findViewById<Button>(R.id.btn_naad_yoga).setOnClickListener {
            val naadYogaFragment = naadYogaFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, naadYogaFragment)
                .addToBackStack(null)
                .commit()
        }
        view.findViewById<Button>(R.id.btnmantra).setOnClickListener {
            val  gayatri_mantra_chanting = gayatri_mantra_chanting()
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, gayatri_mantra_chanting)
                .addToBackStack(null)
                .commit()
        }
        view.findViewById<Button>(R.id.btndhyan).setOnClickListener {
            val  DhyanFragment = DhyanFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, DhyanFragment)
                .addToBackStack(null)
                .commit()
        }
    }
}