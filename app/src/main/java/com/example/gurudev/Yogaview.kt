package com.example.gurudev

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2

class YogaView : Fragment(R.layout.yoga_view) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        // Button to Start Practice (Navigate to AsanaFragment)
        view.findViewById<Button>(R.id.btnStartPower).setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, AsanaFragment()) // Ensure correct container ID
                .addToBackStack(null)
                .commit()
        }
    }
}
