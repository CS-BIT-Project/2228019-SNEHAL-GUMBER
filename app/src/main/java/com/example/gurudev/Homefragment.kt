package com.example.gurudev

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.progressindicator.LinearProgressIndicator

class Homefragment : Fragment() {

    private lateinit var progressIndicator: LinearProgressIndicator
    private lateinit var recyclerViewShorts: RecyclerView
    private lateinit var shortsAdapter: ShortsAdapter

    private val progressReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            updateProgress()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_homefragment, container, false)


        // Initialize Progress Bar
        progressIndicator = view.findViewById(R.id.progressIndicator)

        // Initialize RecyclerView for YouTube Shorts
        recyclerViewShorts = view.findViewById(R.id.recyclerViewShorts)
        setupRecyclerView()

        // Initialize Buttons
        val literatureButton: ImageButton = view.findViewById(R.id.literature)
        val thesisButton: ImageButton = view.findViewById(R.id.thesis)
        val yogaButton: ImageButton = view.findViewById(R.id.yoga)
        val lifestyleButton: ImageButton = view.findViewById(R.id.lifestyle)
        val communityButton: ImageButton = view.findViewById(R.id.community)
        val fab: View = view.findViewById(R.id.fab)

        literatureButton.setOnClickListener { openFragment(LiteratureFragment()) }
        thesisButton.setOnClickListener { openFragment(ThesisFragment()) }
        yogaButton.setOnClickListener { openFragment(Yoga()) }
        lifestyleButton.setOnClickListener { openFragment(Lifestyle()) }
        communityButton.setOnClickListener { openFragment(CommunityFragment()) }
        fab.setOnClickListener { openFragment(live_darshan()) }


        updateProgress()

        return view
    }

    override fun onResume() {
        super.onResume()
        ContextCompat.registerReceiver(
            requireContext(),
            progressReceiver,
            IntentFilter("UPDATE_PROGRESS"),
            ContextCompat.RECEIVER_NOT_EXPORTED
        )
    }

    override fun onPause() {
        super.onPause()
        requireActivity().unregisterReceiver(progressReceiver)
    }

    private fun openFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun setupWebView(webView: WebView, videoId: String) {
        webView.webChromeClient = WebChromeClient()
        webView.webViewClient = WebViewClient()
        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.domStorageEnabled = true
        webSettings.mediaPlaybackRequiresUserGesture = false

        val videoUrl = "https://www.youtube.com/embed/$videoId?autoplay=1&rel=0"
        webView.loadUrl(videoUrl)
    }

    private fun updateProgress() {
        val sharedPreferences = requireContext().getSharedPreferences("LifestylePrefs", Context.MODE_PRIVATE)
        val completedTasks = sharedPreferences.getInt("completed_tasks", 0)
        val totalTasks = 9

        val progress = (completedTasks.toFloat() / totalTasks) * 100
        progressIndicator.progress = progress.toInt()
    }

    private fun setupRecyclerView() {
        val shortsList = listOf(
            "aGnPv8uy5D8",
            "xmQVEbzwRcM",
            "vM1PWMA1RGw",
            "k6uDZPtcZjk",
            "IhRWn5thWro",
            "eisnD76RSoE"
        )

        recyclerViewShorts.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        shortsAdapter = ShortsAdapter(shortsList)
        recyclerViewShorts.adapter = shortsAdapter
    }
}
