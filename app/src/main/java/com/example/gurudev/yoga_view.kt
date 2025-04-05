package com.example.gurudev

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.gurudev.R

class AsanaFragment : Fragment(R.layout.fragment_yoga_asana_adapter) {

    private lateinit var asanaImage: ImageView
    private lateinit var asanaName: TextView
    private lateinit var asanaDescription: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var nextButton: Button

    private var currentAsanaIndex = 0

    // Array of Asanas (Images, Names, and Descriptions)
    private val asanaImages = arrayOf(
        R.drawable.tadasana,
        R.drawable.vajrasana,
        R.drawable.ustrasana,
        R.drawable.ardh_tadasana,
        R.drawable.yoga,
        R.drawable.twisting_cobra_pose_tiryaka_bhujangasana,
        R.drawable.shashankasana_hare_pose_yoga_asana,
        R.drawable.utkatasana
    )

    private val asanaNames = arrayOf(
        "Tadasana",
        "Vajrasana",
        "Ustrasana",
        "Ardh Tadasana",
        "Bhujangasana",
        "Tiryaka Bhujangasana",
        "Shashankasana",
        "Utkatasana"
    )

    private val asanaDescriptions = arrayOf(
        "Stand straight, keep hands by your side, and focus on your breath.",
        "Sit on your heels, keep your back straight, and relax.",
        "Kneel down and arch your back, pushing your hips forward while reaching back to hold your heels.",
        "Stand with feet together, stretch your arms upward, and gently lean back.",
        "Lie on your stomach, place palms under shoulders, and lift your chest while keeping elbows slightly bent.",
        "Lie on your stomach, twist your torso side-to-side while keeping hands planted on the ground.",
        "Sit on your heels, stretch your arms forward, and lower your forehead to the ground.",
        "Stand with feet apart, bend your knees as if sitting on an invisible chair, and hold the posture."
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize UI components
        asanaImage = view.findViewById(R.id.imageView)
        asanaName = view.findViewById(R.id.asanaName)
        asanaDescription = view.findViewById(R.id.asanaDescription)
        progressBar = view.findViewById(R.id.progressBar)
        nextButton = view.findViewById(R.id.btnNext)

        // Set initial values
        updateAsana()


        nextButton.setOnClickListener {
            currentAsanaIndex++
            if (currentAsanaIndex >= asanaImages.size) {
                currentAsanaIndex = 0
            }
            updateAsana()
        }
    }

    private var countDownTimer: CountDownTimer? = null  // Store timer as a class-level variable

    private fun updateAsana() {
        // Update UI elements
        asanaImage.setImageResource(asanaImages[currentAsanaIndex])
        asanaName.text = asanaNames[currentAsanaIndex]
        asanaDescription.text = asanaDescriptions[currentAsanaIndex]


        val totalTime = 30000L  // 30 seconds
        val interval = 100L  // Update every 100ms

        countDownTimer?.cancel()

        countDownTimer = object : CountDownTimer(totalTime, interval) {
            override fun onTick(millisUntilFinished: Long) {
                val progress = ((totalTime - millisUntilFinished) * 100 / totalTime).toInt()
                progressBar.progress = progress
            }

            override fun onFinish() {
                progressBar.progress = 100
                currentAsanaIndex++
                updateAsana()
            }
        }

        countDownTimer?.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        countDownTimer?.cancel()
    }


}